package com.greentree.engine.moon.base.transform;

import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record LookAt(AbstractVector3f vec) implements ConstComponent {
}
