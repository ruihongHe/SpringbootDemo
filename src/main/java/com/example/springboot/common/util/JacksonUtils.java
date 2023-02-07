package com.example.springboot.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonUtils {
    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public JacksonUtils() {
    }

    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                log.warn("Parse Object to String error : {}", var2.getMessage());
                return null;
            }
        }
    }

    public static <T> Map<String, Object> obj2Map(T obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return (Map)objectMapper.readValue(objectMapper.writeValueAsString(obj), Map.class);
            } catch (JsonProcessingException var2) {
                log.warn("Parse Object to String error : {}", var2.getMessage());
                return null;
            }
        }
    }

    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return obj instanceof String ? (String)obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                log.warn("Parse Object to String error : {}", var2.getMessage());
                return null;
            }
        }
    }

    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (!StringUtils.isEmpty(str) && clazz != null) {
            try {
                return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
            } catch (Exception var3) {
                log.warn("Parse String to Object error : {}", var3.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (!StringUtils.isEmpty(str) && typeReference != null) {
            try {
                return typeReference.getType().equals(String.class) ? (T) str : objectMapper.readValue(str, typeReference);
            } catch (IOException var3) {
                log.warn("Parse String to Object error", var3);
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClazz, Class<?>... elementClazzes) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);

        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException var5) {
            log.warn("Parse String to Object error : {}" + var5.getMessage());
            return null;
        }
    }

    static {
        objectMapper.setSerializationInclusion(Include.ALWAYS);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
