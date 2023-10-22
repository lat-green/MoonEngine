package com.greentree.engine.moon.monkey.input;

import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.monkey.Velocity;

@RequiredComponent({Velocity.class})
public record Controller1(float speed) implements ConstComponent {

    public Controller1() {
        this(1);
    }

    public Controller1 {
    }

}
