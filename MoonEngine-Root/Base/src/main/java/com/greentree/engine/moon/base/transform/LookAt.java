package com.greentree.engine.moon.base.transform;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.commons.math.vector.AbstractVector3f;

@RequiredComponent({Transform.class})
public record LookAt(AbstractVector3f vec) implements ConstComponent {
}
