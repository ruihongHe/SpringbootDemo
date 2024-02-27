package com.example.springboot.modules.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 文件服务
 *
 * @author heruihong
 * @createTime 2023-03-27 15:17:06
 */
public interface FileService {
    void onlinePreview(String url, HttpServletResponse response) throws Exception;
}
