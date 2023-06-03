package com.greentree.engine.moon.assets.value;

import com.greentree.commons.data.resource.Resource;
import com.greentree.engine.moon.assets.value.provider.ResourceProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

import java.util.Objects;

public final class ResourceValue implements Value<Resource> {

    private static final long serialVersionUID = 1L;

    private final Resource resource;

    public ResourceValue(Resource resource) {
        Objects.requireNonNull(resource);
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Resource [" + resource + "]";
    }

    @Override
    public ValueProvider<Resource> openProvider() {
        return new ResourceProvider(resource);
    }

    @Override
    public int characteristics() {
        return ResourceProvider.CHARACTERISTICS;
    }

}
