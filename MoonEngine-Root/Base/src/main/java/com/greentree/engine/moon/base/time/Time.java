package com.greentree.engine.moon.base.time;

import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.ecs.annotation.CreateSystem;


@CreateSystem(TimeSystem.class)
public final class Time implements WorldComponent {
	private static final long serialVersionUID = 1L;

	private static final float NANO_TO_SECONDE = 1f / 1_000_000_000;
	private long lastStep;

	private float delta;

	public float delta() {
		return delta;
	}

	public float time() {
		return lastStep;
	}

	void step() {
		final var time = System.nanoTime();
		delta = (time - lastStep) * NANO_TO_SECONDE;
		lastStep = time;
	}

}
