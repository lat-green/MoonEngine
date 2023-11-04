package com.greentree.engine.moon.base.info;

import com.greentree.engine.moon.base.component.UseStage;

import java.util.stream.Stream;

public class AnnotatedCWRDMethodComponentInfo implements CWRDMethodInfo {

    @Override
    public Stream<? extends Class<?>> getCreate(Object object, String method) {
        return UseStage.CREATE.getComponent(object, method);
    }

    @Override
    public Stream<? extends Class<?>> getWrite(Object object, String method) {
        return UseStage.WRITE.getComponent(object, method);
    }

    @Override
    public Stream<? extends Class<?>> getRead(Object object, String method) {
        return UseStage.READ.getComponent(object, method);
    }

    @Override
    public Stream<? extends Class<?>> getDestroy(Object object, String method) {
        return UseStage.DESTROY.getComponent(object, method);
    }

}
