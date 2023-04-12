package com.greentree.engine.moon.ecs.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.greentree.commons.injector.Injector;
import com.greentree.commons.injector.MergeDependencyScanner;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.AnnotationUtil;
import com.greentree.engine.moon.ecs.system.container.FilterDependencyScanner;
import com.greentree.engine.moon.ecs.system.container.WorldComponentDependencyScanner;
import com.greentree.engine.moon.ecs.system.container.WorldInjectionContainer;

public class Systems
		implements InitSystem, UpdateSystem, DestroySystem, Serializable {
	private static final long serialVersionUID = 1L;

	private final List<ECSSystem> systems = new ArrayList<>();
	private final List<InitSystem> initSystems = new ArrayList<>();
	private final List<UpdateSystem> updateSystems = new ArrayList<>();
	private final List<DestroySystem> destroySystems = new ArrayList<>();

	public Systems(ECSSystem...systems) {
		this(IteratorUtil.iterable(systems));
	}
	public Systems(Iterable<? extends ECSSystem> systems) {
		for(var s : systems)
			add(s);
		AnnotationUtil.sort(initSystems, AnnotationUtil.INIT);
		AnnotationUtil.sort(updateSystems, AnnotationUtil.UPDATE);
		AnnotationUtil.sort(destroySystems, AnnotationUtil.DESTROY);
		this.systems.sort(Comparator.comparing(x -> initSystems.indexOf(x)));
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
			inject(world, s);
			s.init(world);
		}
	}

	protected void inject(World world, ECSSystem s) {
		var sc = new MergeDependencyScanner(new WorldComponentDependencyScanner(),
				new FilterDependencyScanner());
		var ctx = new WorldInjectionContainer(world);
		var injector = new Injector(ctx, sc);
		injector.inject(s);
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

	private void add(ECSSystem system) {
		systems.add(system);
		if(system instanceof InitSystem s)
			initSystems.add(s);
		if(system instanceof UpdateSystem s)
			updateSystems.add(s);
		if(system instanceof DestroySystem s)
			destroySystems.add(s);
	}

}
