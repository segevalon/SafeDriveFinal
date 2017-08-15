package com.segeval.safedrive.utils;

import org.apache.log4j.Logger;

public class Log4jRuntime implements Thread.UncaughtExceptionHandler {
    private static Logger log = Log4jHelper.getLogger("Runtime");


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        log.error("Runtime Error" + thread.getName(), ex);
        System.exit(1);
    }
}
