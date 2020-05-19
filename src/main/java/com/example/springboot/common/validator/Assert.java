/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.springboot.common.validator;

import com.example.springboot.common.exception.CmsException;
import com.example.springboot.common.util.ResultCodeEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CmsException(ResultCodeEnum.NULL_POINT.getCode(),message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CmsException(ResultCodeEnum.NULL_POINT.getCode(),message);
        }
    }
}
