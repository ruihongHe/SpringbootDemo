package com.example.springboot.lambda;

import java.util.function.Function;

public class function {
    public static void main(String[] args) {
        String string = testFunction("哈哈哈哈哈哈哈", (str) -> str.substring(0, str.length() - 2));
        System.out.println("---->" + string);
    }

    public static String testFunction(String str, Function<String, String> function) {
        return function.apply(str);
    }
}
