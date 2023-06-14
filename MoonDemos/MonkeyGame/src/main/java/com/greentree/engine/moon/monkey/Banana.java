package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.collision2d.ColliderComponent;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.mesh.SpriteRenderer;

@RequiredComponent({Transform.class, ColliderComponent.class, SpriteRenderer.class})
public record Banana() implements ConstComponent {

}
