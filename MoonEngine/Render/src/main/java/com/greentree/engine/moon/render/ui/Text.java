package com.greentree.engine.moon.render.ui;

import com.greentree.engine.moon.ecs.component.ConstComponent;

public record Text(String value, Font font) implements ConstComponent {

}
