package com.greentree.engine.moon.ecs.component;

import java.io.Serializable;

public interface Component extends Serializable {
	
	default boolean copyTo(Component other) {
		return false;
	}
	
	Component copy();
	
}
