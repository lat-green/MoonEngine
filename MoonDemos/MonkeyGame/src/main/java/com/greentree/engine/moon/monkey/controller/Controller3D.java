package com.greentree.engine.moon.monkey.controller;

import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record Controller3D(float speed) implements ConstComponent {

    public static final float BORDER = Mathf.PI / 2 * 0.999f;

    public Controller3D() {
        this(1);
    }

    public Controller3D {
    }

}
