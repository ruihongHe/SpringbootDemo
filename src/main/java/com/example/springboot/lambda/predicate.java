package com.example.springboot.lambda;

import java.util.function.Predicate;

public class predicate {
    public static void main(String[] args) {
        System.out.println(testPredicate("测试", (str) -> str.length() == 3));
    }

    public static boolean testPredicate(String string, Predicate<String> predicate) {
        return predicate.test(string);
    }
}
