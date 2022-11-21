package com.fx.tetris.utils;

import com.almasb.fxgl.input.Input;

import java.io.InputStream;
import java.util.Objects;

public class FileUtils {

    /**
     * 获取图片
     * @param path
     * @return
     */
    public static InputStream getStream(String path){
        return FileUtils.class.getResourceAsStream(path);
    }

    public static String getResource(String path){
        return Objects.requireNonNull(FileUtils.class.getResource(path)).toString();
    }
}
