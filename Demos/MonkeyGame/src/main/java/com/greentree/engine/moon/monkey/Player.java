package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public final class Player implements ConstComponent {

    public int score = 0;

}
