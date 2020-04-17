package com.example.springboot.lambda;

import java.util.function.Consumer;

public class consumer {
    public static void main(String[] args) {
        testConsumer(9999, (x) -> {
            System.out.println("打游戏花费：" + x);
        });
    }

    public static void testConsumer(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }
}
