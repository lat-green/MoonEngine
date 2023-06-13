package com.greentree.engine.moon.monkey;

import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.Component;

@RequiredComponent({Transform.class})
public class Velocity implements Component {

    public final Vector3f value = new Vector3f();

    @Override
    public Component copy() {
        return null;
    }

}
