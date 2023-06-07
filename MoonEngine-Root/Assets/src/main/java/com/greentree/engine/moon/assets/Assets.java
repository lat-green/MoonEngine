package com.greentree.engine.moon.assets;

import com.greentree.commons.reflection.info.TypeInfo;

import java.io.File;

public class Assets {

    private static final File tempDirectory = new File(System.getProperty("java.io.tmpdir"), "MoonEngine/Assets/" + new File("").getAbsoluteFile().getName());

    public static <T> File getTempCacheDirectory(TypeInfo<T> type) {
        return new File(tempDirectory, type.getName());
    }

}
