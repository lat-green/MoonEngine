package com.greentree.engine.moon.render.mesh;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.material.Property;

import java.util.Objects;

@RequiredComponent({Transform.class})
public record SpriteRenderer(Asset<Property> texture) implements ConstComponent {

    public SpriteRenderer {
        Objects.requireNonNull(texture);
    }

}
