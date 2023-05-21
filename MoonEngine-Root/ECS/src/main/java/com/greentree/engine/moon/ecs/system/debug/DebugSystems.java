package com.greentree.engine.moon.ecs.system.debug;

import java.util.List;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.Systems;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class DebugSystems extends Systems {


	private final SystemsProfiler profiler;

	public DebugSystems(SystemsProfiler profiler, List<? extends InitSystem> initSystems,
			List<? extends UpdateSystem> updateSystems, List<? extends DestroySystem> destroySystems) {
		super(initSystems, updateSystems, destroySystems);
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
