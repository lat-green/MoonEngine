package com.greentree.engine.moon.ecs.system;

import java.util.ArrayList;
import java.util.List;

import com.greentree.engine.moon.ecs.World;

public class Systems implements InitSystem, UpdateSystem, DestroySystem {

	private final List<InitSystem> initSystems;
	private final List<UpdateSystem> updateSystems;
	private final List<DestroySystem> destroySystems;

	public Systems(List<? extends InitSystem> initSystems, List<? extends UpdateSystem> updateSystems,
			List<? extends DestroySystem> destroySystems) {
		this.initSystems = new ArrayList<>(initSystems);
		this.updateSystems = new ArrayList<>(updateSystems);
		this.destroySystems = new ArrayList<>(destroySystems);
	}
	
	@Override
	public void destroy() {
		final var iter = destroySystems.iterator();
		while(iter.hasNext())
			iter.next().destroy();
	}

	public Iterable<? extends DestroySystem> destroySystems() {
		return destroySystems;
	}

	@Override
	public void init(World world) {
		final var iter = initSystems.iterator();
		while(iter.hasNext()) {
			var s = iter.next();
			s.init(world);
		}
	}
	
	public Iterable<? extends InitSystem> initSystems() {
		return initSystems;
	}

	@Override
	public void update() {
		final var iter = updateSystems.iterator();
		while(iter.hasNext())
			iter.next().update();
	}

	public Iterable<? extends UpdateSystem> updateSystems() {
		return updateSystems;
	}

}
