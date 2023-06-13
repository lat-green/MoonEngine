package com.greentree.engine.moon.monkey.input;

import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.monkey.Velocity;

@RequiredComponent({Velocity.class})
public record Controller(float speed) implements ConstComponent {

    public static final float BORDER = Mathf.PI / 2 * 0.999f;

    public Controller() {
        this(1);
    }

    public Controller {
    }

}
