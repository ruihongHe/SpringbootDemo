package com.example.springboot.modules.controller;

import com.example.springboot.modules.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 文件服务控制类
 *
 * @author heruihong
 * @createTime 2023-03-27 15:19:46
 */

public class FileController {


    @Autowired
    private FileService fileService;

    @ApiOperation(value = "系统文件在线预览接口")
    @PostMapping("/api/file/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception{
        fileService.onlinePreview(url,response);
    }
}
