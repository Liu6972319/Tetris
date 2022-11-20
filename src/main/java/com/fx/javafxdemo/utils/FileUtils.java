package com.fx.javafxdemo.utils;

import javafx.scene.image.Image;

import java.io.InputStream;

public class FileUtils {

    /**
     * 获取图片
     * @param path
     * @return
     */
    public static InputStream getImage(String path){
        return FileUtils.class.getResourceAsStream(path);
    }
}
