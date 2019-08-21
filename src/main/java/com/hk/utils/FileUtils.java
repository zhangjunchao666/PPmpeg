package com.hk.utils;

import java.io.File;

/**
 * @author 胡冉
 * @ClassName FileUtils
 * @Description: TODO
 * @Date 2019/5/8 13:11
 * @Version 2.0
 */
public class FileUtils {
    private static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
}
