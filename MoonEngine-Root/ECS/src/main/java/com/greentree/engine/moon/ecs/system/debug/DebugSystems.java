package com.greentree.engine.moon.ecs.system.debug;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.ecs.system.Systems;

public class DebugSystems extends Systems {



	private static final long serialVersionUID = 1L;

	private final SystemsProfiler profiler;

	public DebugSystems(SystemsProfiler profiler, ECSSystem... systems) {
		super(systems);
		this.profiler = profiler;
	}

	public DebugSystems(SystemsProfiler profiler, Iterable<? extends ECSSystem> systems) {
		super(systems);
		this.profiler = profiler;
	}

	@Override
	public void update() {
		final var iter = updateSystems().iterator();
		try(final var updateFrofiler = profiler.update();){
			while(iter.hasNext()) {
				final var s = iter.next();
				try(final var start = updateFrofiler.start(s);) {
					s.update();
				}
			}
		}
	}

	@Override
	public void init(World world) {
		final var iter = initSystems().iterator();
		try(final var updateFrofiler = profiler.init();){
			while(iter.hasNext()) {
				final var s = iter.next();
				try(final var start = updateFrofiler.start(s);) {
					inject(world, s);
					s.init(world);
				}
			}
		}
	}
	
	@Override
	public void destroy() {
		final var iter = destroySystems().iterator();
		try(final var updateFrofiler = profiler.destroy();){
			while(iter.hasNext()) {
				final var s = iter.next();
				try(final var start = updateFrofiler.start(s);) {
					s.destroy();
				}
			}
		}
	}
	
}
