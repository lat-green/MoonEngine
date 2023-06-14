package com.greentree.engine.moon.monkey.input;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.component.ConstComponent;

import java.util.Collection;

@RequiredComponent({Transform.class})
public record Follow(Collection<WorldEntity> targets, float speed) implements ConstComponent {
    
}
