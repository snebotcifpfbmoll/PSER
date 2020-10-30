package com.serafinebot.pser.repaso.util;

import java.io.File;

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

    public File file(String path) {
        File file = null;
        try {
           file = new File(path);
        } catch (Exception e) {
            System.out.printf("%s: %s", path, e.getMessage());
        }
        return file;
    }

    public File resourceFile(String fileName) {
        return file(String.format("%s%s", projectSource, fileName));
    }
}
