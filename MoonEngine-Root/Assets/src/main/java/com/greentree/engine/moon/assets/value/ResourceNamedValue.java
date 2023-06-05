package com.greentree.engine.moon.assets.value;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.engine.moon.assets.value.provider.ResourceNamedProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class ResourceNamedValue implements Value<Resource> {

    private static final long serialVersionUID = 1L;
    private final ResourceLocation resources;
    private final Value<? extends String> name;

    private ResourceNamedValue(ResourceLocation resources, Value<? extends String> name) {
        this.name = name;
        this.resources = resources;
    }

    public static Value<Resource> newValue(ResourceLocation resources, Value<? extends String> name) {
        if (name.hasCharacteristics(CONST)) {
            var nameString = name.get();
            final var resource = resources.getResource(nameString);
            if (resource == null)
                throw new NullPointerException("resource " + name + "(" + nameString + ") not found");
            return new ResourceValue(resource);
        }
        return new ResourceNamedValue(resources, name);
    }

    @Override
    public String toString() {
        return "Resource [" + name + "]";
    }

    @Override
    public int characteristics() {
        return ResourceNamedProvider.CHARACTERISTICS;
    }

    @Override
    public ValueProvider<Resource> openProvider() {
        return ResourceNamedProvider.newResourceProvider(resources, name);
    }

}
