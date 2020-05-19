package com.example.springboot.common.banner;

import lombok.SneakyThrows;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

public class MyBanner implements Banner {

    private static final String BANNER =
            " ______      _____   __    __              ______     __    __    _____  \n" +
                    "(_  __ \\    / ___/   ) )  ( (             (   __ \\    ) )  ( (   (_   _) \n" +
                    "  ) ) \\ \\  ( (__    ( (    ) )  ________   ) (__) )  ( (    ) )    | |   \n" +
                    " ( (   ) )  ) __)    \\ \\  / /  (________) (    __/    ) )  ( (     | |   ";

    private static final String BANNER1 =
                    "  ) )  ) ) ( (        \\ \\/ /              ) \\ \\  _  ( (   ) )    | |   \n" +
                    " / /__/ /   \\ \\___    \\  /              ( ( \\ \\_)) ) \\__/ (   _| |__ \n" +
                    "(______/     \\____\\    \\/                )_) \\__/   \\______/  /_____( \n";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(BANNER);
        out.println(BANNER1);
        out.println();
    }
    
}
