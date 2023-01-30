package com.greentree.commons.assets.value;

import com.greentree.commons.assets.value.provider.ResourceNamedProvider;
import com.greentree.commons.assets.value.provider.ValueProvider;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;


public final class ResourceNamedValue implements Value<Resource> {
	
	private static final long serialVersionUID = 1L;
	private final ResourceLocation resources;
	private final Value<? extends String> name;
	
	private ResourceNamedValue(ResourceLocation resources, Value<? extends String> name) {
		this.name = name;
		this.resources = resources;
	}
	
	public static Value<Resource> newValue(ResourceLocation resources,
			Value<? extends String> name) {
		if(name.hasCharacteristics(CONST)) {
			@SuppressWarnings("deprecation")
			final var resource = resources.getResource(name.get());
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
		return ResourceNamedProvider.newProvider(resources, name);
	}
	
}
