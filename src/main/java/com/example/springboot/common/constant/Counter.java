package com.example.springboot.common.constant;

import lombok.Getter;

import java.util.BitSet;

/**
 * 逐行文件处理公共类
 *
 * @author heruihong
 * @createTime 2024-03-29 17:23:12
 */
public class Counter {
    @Getter
    private String serviceName;
    @Getter
    private long numberOfCalls;
    private final BitSet daysWithCalls;

    public Counter(final String serviceName, final int numberOfDays) {
        this.serviceName = serviceName;
        this.numberOfCalls = 0L;
        daysWithCalls = new BitSet(numberOfDays);
    }

    public void add() {
        numberOfCalls++;
    }

    public void setDay(final int dayNumber) {
        daysWithCalls.set(dayNumber);
    }

    public boolean allDaysSet() {
        return daysWithCalls.stream()
                .mapToObj(index -> daysWithCalls.get(index))
                .reduce(Boolean.TRUE, Boolean::logicalAnd);
    }


}
