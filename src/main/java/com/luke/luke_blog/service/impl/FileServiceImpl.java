package com.luke.luke_blog.service.impl;

import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IFileService;
import com.luke.luke_blog.utils.IdWorker;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Administrator
 * @version 1.0
 * @date 2021/6/9 15:30
 */
@Service("fileService")
public class FileServiceImpl implements IFileService {

    @Value("${luke.blog.tencent.file.SECRET_ID}")
    private String secretId;
    @Value("${luke.blog.tencent.file.SECRET_KEY}")
    private String secretKey;
    @Value("${luke.blog.tencent.file.REGION}")
    private String region;
    @Value("${luke.blog.tencent.file.BUCKET_NAME}")
    private String bucketName;
    @Value("${luke.blog.tencent.file.REQUEST_PATH}")
    private String requestPath;
    @Value("${luke.blog.tencent.file.PREFIX}")
    private String prefix;

    @Resource
    IdWorker idWorker;

    @Override
    public ResponseResult uploadFile(MultipartFile file) {
        if (file == null) {
            return ResponseResult.FAILURE("文件不存在");
        }
        String oldFileName = file.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = idWorker.nextId() + eName;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        //获取部分属性
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        String bucketName = this.bucketName;

        File localFile = null;
        try {
            localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);
            String key = "/" + this.prefix + "/" + year + "/" + month + "/" + day + "/" + newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            cosClient.putObject(putObjectRequest);
            String fileUrl = this.requestPath+putObjectRequest.getKey();
            Map<String, String> map = new HashMap<>();
            map.put("fileUrl", fileUrl);
            return ResponseResult.SUCCESS("上传成功",map);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseResult.FAILURE("上传失败,请联系管理员,错误信息:"+e.getMessage());
        }finally {
            cosClient.shutdown();
        }
    }

    @Override
    public ResponseResult deleteFile(String fileUrl) {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        fileUrl = fileUrl.replace(requestPath,"");
        try {
            cosClient.deleteObject(this.bucketName, fileUrl);
            return ResponseResult.SUCCESS("删除成功!", null);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseResult.FAILURE("删除失败"+e.getMessage());
        }finally {
            cosClient.shutdown();
        }
    }
}
