package com.greentree.engine.moon.assets.value.provider;

import com.greentree.commons.data.resource.Resource;

import java.util.Objects;
import java.util.function.Consumer;

public final class ResourceProvider implements ValueProvider<Resource> {

    public static final int CHARACTERISTICS = NOT_NULL | CACHED;
    private final Resource resource;
    private transient long lastModified;

    public ResourceProvider(Resource resource) {
        Objects.requireNonNull(resource);
        this.resource = resource;
    }

    @Override
    public int characteristics() {
        return CHARACTERISTICS;
    }

    @Override
    public String toString() {
        return resource.toString();
    }

    @Override
    public Resource get() {
        lastModified = resource.lastModified();
        return resource;
    }

    @Override
    public ValueProvider<Resource> copy() {
        return new ResourceProvider(resource);
    }

    @Override
    public boolean tryGet(Consumer<? super Resource> action) {
        final var lastModified = resource.lastModified();
        if (lastModified > this.lastModified) {
            this.lastModified = lastModified;
            action.accept(resource);
            return true;
        }
        return false;
    }

    @Override
    public boolean isChenge() {
        final var lastModified = resource.lastModified();
        return lastModified > this.lastModified;
    }

}
