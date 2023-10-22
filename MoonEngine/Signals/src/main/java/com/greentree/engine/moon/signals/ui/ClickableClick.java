package com.greentree.engine.moon.signals.ui;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.component.ConstComponent;


public record ClickableClick(Entity button) implements ConstComponent {
	
}
