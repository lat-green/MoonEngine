package com.greentree.engine.moon.base.component;

import com.greentree.engine.moon.base.property.modules.UseProperty;
import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.ecs.scene.SceneProperty;
import com.greentree.engine.moon.kernel.AnnotationUtil;

import java.lang.reflect.Method;
import java.util.stream.Stream;

public enum UseStage {

    CREATE, WRITE, READ, DESTROY;

    public Stream<Class<? extends Component>> getComponent(Object obj, String method) {
        return getComponent(obj.getClass(), method);
    }

    public Stream<Class<? extends Component>> getComponent(Class<?> cls, String method) {
        return getComponent(getMethod(cls, method));
    }

    public Stream<Class<? extends Component>> getComponent(Method method) {
        return AnnotationUtil.getAnnotations(method, UseComponent.class).filter(x -> x.state() == this)
                .flatMap(x -> Stream.of(x.value()));
    }

    private static Method getMethod(Class<?> cls, String method) {
        for (var m : cls.getDeclaredMethods())
            if (m.getName().equals(method))
                return m;
        for (var m : cls.getMethods())
            if (m.getName().equals(method))
                return m;
        throw new RuntimeException("no such method " + cls + " name: " + method);
    }

    public Stream<Class<? extends SceneProperty>> getProperty(Object obj, String method) {
        return getProperty(obj.getClass(), method);
    }

    public Stream<Class<? extends SceneProperty>> getProperty(Class<?> cls, String method) {
        return getProperty(getMethod(cls, method));
    }

    public Stream<Class<? extends SceneProperty>> getProperty(Method method) {
        return AnnotationUtil.getAnnotations(method, UseProperty.class).filter(x -> x.state() == this)
                .flatMap(x -> Stream.of(x.value()));
    }
}
