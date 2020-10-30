package com.serafinebot.pser.repaso.util;

public class FileUtils {
    private static FileUtils instance = null;

    // this is not good
    private final String projectSource = "/Users/hystrix/Development/DAM/PSER/repaso/src/main/resources/";

    private FileUtils() {}

    public static FileUtils getInstance() {
        if (instance == null) instance = new FileUtils();
        return instance;
    }

    public String resourceDirectory() {
        return projectSource;
    }
}
