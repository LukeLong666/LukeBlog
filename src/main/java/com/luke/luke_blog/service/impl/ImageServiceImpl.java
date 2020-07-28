package com.luke.luke_blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.luke_blog.dao.ImageMapper;
import com.luke.luke_blog.pojo.Image;
import com.luke.luke_blog.pojo.User;
import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IImageService;
import com.luke.luke_blog.service.IUserService;
import com.luke.luke_blog.utils.Constants;
import com.luke.luke_blog.utils.IdWorker;
import com.luke.luke_blog.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * impl形象服务
 *
 * @author zhang
 * @date 2020/07/28
 */
@Service("imageService")
@Transactional
public class ImageServiceImpl implements IImageService {

    @Value("${luke.blog.image.save-path}")
    public String imagePath;
    @Value("${luke.blog.image.max-size}")
    public long imageMaxSize;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");

    public static final String TAG="ImageServiceImpl --> ";

    Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Resource
    private IdWorker idWorker;

    @Resource
    private ImageMapper imageDao;

    @Resource
    private IUserService userService;

    /**
     * 上传图片
     * 上传的路径:
     * 上传的内容:
     * 保存记录到数据库里
     * ID / 存储路径 / url / 原名称 / 用户ID / 状态 / 创建日期 / 更新日期
     * @param file 文件
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult uploadImage(MultipartFile file) {
        log.info(TAG+" uploadImage() --> imagePath : "+imagePath);
        log.info(TAG+" uploadImage() --> imageMaxSize : "+imageMaxSize);
        //判断是否有文件
        if (file == null) {
            return ResponseResult.FAILURE("图片不可以为空");
        }
        //判断文件类型,只支持图片上传,
        String contentType = file.getContentType();
        if (TextUtils.isEmpty(contentType)) {
            return ResponseResult.FAILURE("图片格式错误");
        }
        log.info(TAG+" uploadImage() --> contentType : "+contentType);

        //获取相关数据,文件类型,文件名称,
        String originalFileName = file.getOriginalFilename();
        log.info(TAG+" uploadImage() --> originalFileName : "+originalFileName);
        String type = getType(contentType, originalFileName);
        if(type==null){
            return ResponseResult.FAILURE("不支持该文件类型");
        }
        //限制文件的大小
        long size = file.getSize();
        log.info(TAG+" uploadImage() --> size : "+size);
        if (size > imageMaxSize) {
            return ResponseResult.FAILURE("图片最大仅支持" + (imageMaxSize / 1024 / 1024) + "MB");
        }
        //创建图片的保存目录
        //规则: 配置目录/日期/类型/ID.类型
        long currentTimeMillis = System.currentTimeMillis();
        String currentDay = simpleDateFormat.format(currentTimeMillis);
        log.info(TAG+" uploadImage() --> currentDay : "+currentDay);

        //判断日期文件夹是否存在
        String dayPath = imagePath + File.separator + currentDay;
        File dayPathFile = new File(dayPath);
        if (!dayPathFile.exists()) {
            dayPathFile.mkdirs();
        }
        //判断类型文件夹是否存在
        //根据定的规则进行命名
        String ID = idWorker.nextId()+"";
        String targetPath = dayPath+File.separator+type+File.separator+ID+"."+ type;
        File targetFile = new File(targetPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.mkdirs();
        }
        log.info(TAG+" uploadImage() --> targetFile : "+targetFile);
        //保存文件
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            file.transferTo(targetFile);
            //返回结果:包含这个图片的名称和访问路径
            Map<String, String> reuslt = new HashMap<>();
            //访问路径
            String resultPath = currentTimeMillis + "_" + ID + "." + type;
            reuslt.put("id", resultPath);
            //名称  ---> alt="图片描述"
            reuslt.put("name", originalFileName);
            log.info(TAG+" uploadImage() --> reuslt : "+reuslt);
            //保存记录到数据库
            Image image = new Image();
            image.setContentType(contentType);
            image.setId(ID);
            image.setCreateTime(new Date());
            image.setUpdateTime(new Date());
            image.setPath(targetFile.getPath());
            image.setName(originalFileName);
            image.setUrl(resultPath);
            image.setState("1");
            log.info(TAG+" uploadImage() --> image : "+image);
            User user = userService.checkUser();
            if (user != null) {
                log.info(TAG+" uploadImage() --> user : "+user);
            }
            image.setUserId("735424583653392384");
            int saveResult = imageDao.save(image);
            log.info(TAG+" uploadImage() --> saveResult : "+saveResult);
            return ResponseResult.SUCCESS("上传成功",reuslt);
        } catch (IOException e) {
            log.error(TAG+" uploadImage() --> transferTo() : "+"上传失败"+" ==> "+e.toString());
            e.printStackTrace();
        }
        return ResponseResult.FAILURE("图片上传失败,请重试");
    }

    private String getType(String contentType, String originalFileName) {
        String type = null;
        if(Constants.ImageType.TYPE_PNG_WITH_PREFIX.equals(contentType)&&originalFileName.endsWith(Constants.ImageType.TYPE_PNG)){
            type = Constants.ImageType.TYPE_PNG;
        }else if(Constants.ImageType.TYPE_JPG_WITH_PREFIX.equals(contentType)&&originalFileName.endsWith(Constants.ImageType.TYPE_JPG)){
            type = Constants.ImageType.TYPE_JPG;
        }else if(Constants.ImageType.TYPE_JPEG_WITH_PREFIX.equals(contentType)&&originalFileName.endsWith(Constants.ImageType.TYPE_JPG)){
            type = Constants.ImageType.TYPE_JPG;
        }
        return type;
    }

    @Override
    public void viewImage(HttpServletResponse response, String imageId) {
        //需要日期
        String[] paths = imageId.split("_");
        String dayValue = paths[0];
        log.info(TAG+" viewImage() --> dayValue : "+dayValue);
        String day = simpleDateFormat.format(Long.parseLong(dayValue));
        log.info(TAG+" viewImage() --> day : "+day);
        String name = paths[1];
        log.info(TAG+" viewImage() --> name : "+name);
        //需要类型
        String type = name.substring(name.length() - 3);
        log.info(TAG+" viewImage() --> type : "+type);
        String targetPath = imagePath + File.separator + day+File.separator + type +File.separator+name;
        log.info(TAG+" viewImage() --> targetPath : "+targetPath);
        //ID
        //使用日期的时间戳
        File file = new File(targetPath);
        OutputStream writer = null;
        FileInputStream fos = null;
        try {
            response.setContentType("image/"+type);
            writer = response.getOutputStream();
            fos = new FileInputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while((len=fos.read(buff))!=-1){
                writer.write(buff,0,len);
            }
            writer.flush();
            log.info(TAG+" viewImage() -->  "+"写入成功");
        } catch (Exception e) {
            log.error(TAG+" viewImage() --> : "+"写入失败"+" ==> "+e.toString());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error(TAG+" viewImage() --> : "+"writer 关闭失败"+" ==> "+e.toString());
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error(TAG+" viewImage() --> : "+"fos 关闭失败"+" ==> "+e.toString());
                }
            }
        }
    }

    @Override
    public ResponseResult listImages(int page, int size) {
        //获取用户列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        PageHelper.startPage(page, size);
        // TODO: 2020/7/28 条件加上根据当前userId获取图片(sql)
        List<Image> imageList = imageDao.findAll();
        PageInfo<Image> pageInfo = new PageInfo<>(imageList);
        log.info("PageInfo =====> "+pageInfo.toString());
        return ResponseResult.SUCCESS("查询成功!", pageInfo);
    }

    @Override
    public ResponseResult deleteImageById(String imageId) {
        if (TextUtils.isEmpty(imageId)) {
            log.info(TAG+" deleteImageById() -->  "+"ID为空");
            return ResponseResult.FAILURE("图片id不能为空");
        }
        int result = imageDao.deleteById(imageId);
        log.info(TAG+" deleteImageById() --> result:  "+"result");
        return result>0?ResponseResult.SUCCESS("删除成功!",result):ResponseResult.FAILURE("删除失败");
    }


}
