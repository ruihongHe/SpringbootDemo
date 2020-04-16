package com.example.springboot.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class supplier {
    public static void main(String[] args) {
        List<Integer> list = testSupplier(10, () -> (int)(Math.random() * 100));
        list.forEach(System.out::println);
    }

    public static List<Integer> testSupplier(int num, java.util.function.Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i ++) {
            Integer n = supplier.get();
            list.add(n);
        }
        return list;
    }
}
