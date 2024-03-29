package com.example.springboot.common.util;

import com.example.springboot.common.constant.Counter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cn.hutool.core.lang.Console.print;

/**
 * 逐行文件处理工具类
 *
 * @author heruihong
 * @createTime 2024-03-29 17:25:23
 */
public class ProcessFilesUtil {

    private void processFiles(final List<File> fileList) {
        final Map<String, Counter> compiledMap = new HashMap<>();

        for (int i = 0; i < fileList.size(); i++) {
            processFile(fileList, compiledMap, i);
        }

        final List<Counter> topCalls =
                compiledMap.values().stream()
                        .filter(Counter::allDaysSet)
                        .sorted(Comparator.comparing(Counter::getNumberOfCalls).reversed())
                        .limit(10)
                        .toList();

        print(topCalls);
    }

    private void processFile(final List<File> fileList,
                             final Map<String, Counter> compiledMap,
                             final int dayNumber) {
        try (
                Stream<String> lineStream = Files.lines(fileList.get(dayNumber).toPath())) {
            lineStream.map(this::toLogLine)
                    .forEach(
                            logLine -> {
                                Counter counter = compiledMap.get(logLine.serviceName());
                                if (counter == null) {
                                    counter = new Counter(logLine.serviceName(), fileList.size());
                                    compiledMap.put(logLine.serviceName(), counter);
                                }
                                counter.add();
                                counter.setDay(dayNumber);
                            });

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Counter toLogLine(String s) {
        Counter counter=new Counter(s,0);

        return counter;
    }


}
