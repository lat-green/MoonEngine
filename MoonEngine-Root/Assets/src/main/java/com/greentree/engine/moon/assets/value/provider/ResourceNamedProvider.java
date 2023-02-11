package com.greentree.engine.moon.assets.value.provider;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.engine.moon.assets.value.ResourceValue;
import com.greentree.engine.moon.assets.value.Value;

public final class ResourceNamedProvider implements ValueProvider<ResourceValue> {
	
	
	public static final int CHARACTERISTICS = 0;
	
	private final ResourceLocation resources;
	private final ValueProvider<? extends String> name;
	
	private ResourceNamedProvider(ResourceLocation resources, ValueProvider<? extends String> name) {
		this.name = name;
		this.resources = resources;
	}
	
	public static ValueProvider<Resource> newResourceProvider(ResourceLocation resources,
			Value<? extends String> name) {
		return newResourceProvider(resources, name.openProvider());
	}
	
	private static ValueProvider<Resource> newResourceProvider(ResourceLocation resources,
			ValueProvider<? extends String> name) {
		if(name.hasCharacteristics(CONST)) {
			final var resource = resources.getResource(name.get());
			return new ResourceProvider(resource);
		}
		return ReduceProvider.newProvider(newProvider(resources, name));
	}
	
	public static ValueProvider<ResourceValue> newProvider(ResourceLocation resources, Value<? extends String> name) {
		return newProvider(resources, name.openProvider());
	}
	
	private static ValueProvider<ResourceValue> newProvider(ResourceLocation resources,
			ValueProvider<? extends String> name) {
		return new ResourceNamedProvider(resources, name);
	}
	
	@Override
	public int characteristics() {
		return name.characteristics() | CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		name.close();
	}
	
	@Override
	public ResourceValue get() {
		final var name = this.name.get();
		if(name == null)
			return null;
		final var resource = resources.getResource(name);
		if(resource == null)
			throw new IllegalArgumentException("resource not found " + name);
		return new ResourceValue(resource);
	}
	
	@Override
	public boolean isChenge() {
		return name.isChenge();
	}
	
	@Override
	public String toString() {
		return "ResourceProvider [" + name + "]";
	}
	
	@Override
	public ValueProvider<ResourceValue> copy() {
		return newProvider(resources, name.copy());
	}
}
