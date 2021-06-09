package com.luke.luke_blog.service;

import com.luke.luke_blog.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 腾讯COS文件存储服务
 *
 * @author zxl
 * @date 2021/06/09
 */
public interface IFileService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link ResponseResult}
     */
    ResponseResult uploadFile(MultipartFile file);

    /**
     * 删除文件
     *
     * @param fileUrl 文件的url
     * @return {@link ResponseResult}
     */
    ResponseResult deleteFile(String fileUrl);
}
