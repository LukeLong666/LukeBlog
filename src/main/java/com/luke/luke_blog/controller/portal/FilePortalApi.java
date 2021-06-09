package com.luke.luke_blog.controller.portal;

import com.luke.luke_blog.response.ResponseResult;
import com.luke.luke_blog.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @version 1.0
 * @date 2021/6/9 15:43
 */
@Api(tags = "腾讯COS文件存储服务")
@RestController
@RequestMapping("/portal/file")
public class FilePortalApi {

    @Resource
    IFileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/file")
    public ResponseResult file(MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @ApiOperation("文件删除")
    @DeleteMapping("/file")
    public ResponseResult file(String fileUrl) {
        return fileService.deleteFile(fileUrl);
    }
}
