package com.example.springboot.common.util;


import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author heruihong
 * @Date 2020/5/5 23:53
 * @Version 1.0
 */
@Log4j2
public class ExceptionUtil {

    /**
     * 打印异常信息  
     */
    public static String getMessage(Exception e){
        String swStr = null;
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            swStr = sw.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return swStr;

    }

}
