package com.segeval.safedrive.utils;

import android.os.Environment;
import android.util.Log;

import org.apache.log4j.Logger;

import de.mindpipe.android.logging.log4j.LogConfigurator;



public class Log4jHelper {
    public final static String logFileName = "safedrive.log";
    private final static LogConfigurator CONFIGURATOR = new LogConfigurator();

    static {
        configureLog4j();
    }

    private static void configureLog4j() {
        String filename = Environment.getExternalStorageDirectory() + "/" + logFileName;
        Log.d("TAG", filename);
        String filepattern = "%d - [%c] - %p : %m%n";
        int maxBackupSize = 10;
        long maxFileSize = 1024 * 1024;
        configure(filename, filepattern, maxBackupSize, maxFileSize);
    }

    private static void configure(String fileName, String filePattern, int maxBackupSize, long maxFileSize) {
        CONFIGURATOR.setFileName(fileName);
        CONFIGURATOR.setMaxFileSize(maxFileSize);
        CONFIGURATOR.setMaxBackupSize(maxBackupSize);
        CONFIGURATOR.setFilePattern(filePattern);
        CONFIGURATOR.setUseLogCatAppender(true);
        CONFIGURATOR.configure();
    }

    public static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.trace(System.err);
        return logger;
    }

}
