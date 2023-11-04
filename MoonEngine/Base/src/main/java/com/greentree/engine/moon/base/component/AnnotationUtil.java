package com.greentree.engine.moon.base.component;

import com.greentree.engine.moon.base.EngineBase;

import java.util.List;

@Deprecated
public class AnnotationUtil {

    public static final String INIT = "init";
    public static final String UPDATE = "update";
    public static final String DESTROY = "destroy";

    public static <T> void sortInit(List<T> list) {
        sort(list, INIT);
    }

    public static <T> void sort(List<T> list, String method) {
        EngineBase.SORTER.sort(list, method);
    }

    public static <T> void sortUpdate(List<T> list) {
        sort(list, UPDATE);
    }

    public static <T> void sortDestroy(List<T> list) {
        sort(list, DESTROY);
    }

}
