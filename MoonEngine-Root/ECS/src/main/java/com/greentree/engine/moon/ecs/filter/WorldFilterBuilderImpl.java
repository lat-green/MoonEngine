package com.greentree.engine.moon.ecs.filter;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;

public record WorldFilterBuilderImpl(World world, FilterBuilder builder) implements WorldFilterBuilder {
	
	
	@SuppressWarnings("deprecation")
	public WorldFilterBuilderImpl(World world) {
		this(world, new FilterBuilder());
	}
	
	@Override
	public WorldFilterBuilder ignore(Class<? extends Component> cls) {
		builder.ignore(cls);
		return this;
	}
	
	@Override
	public WorldFilterBuilder required(Class<? extends Component> cls) {
		builder.required(cls);
		return this;
	}
	
	@Override
	public Filter build() {
		return builder.build(world);
	}
	
}
