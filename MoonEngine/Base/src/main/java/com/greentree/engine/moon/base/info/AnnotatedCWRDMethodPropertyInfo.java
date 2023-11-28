package com.greentree.engine.moon.base.info;

import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.DestroyProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.kernel.AnnotationUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class AnnotatedCWRDMethodPropertyInfo implements CWRDMethodInfo {

    @Override
    public Stream<? extends Class<?>> getCreate(Object object, String method) {
        return UseStage.CREATE.getProperty(object, method);
    }

    @Override
    public Stream<? extends Class<?>> getWrite(Object object, String method) {
        return UseStage.WRITE.getProperty(object, method);
    }

    @Override
    public Stream<? extends Class<?>> getRead(Object object, String method) {
        return UseStage.READ.getProperty(object, method);
    }

    @Override
    public Stream<? extends Class<?>> getDestroy(Object object, String method) {
        return UseStage.DESTROY.getProperty(object, method);
    }

}
