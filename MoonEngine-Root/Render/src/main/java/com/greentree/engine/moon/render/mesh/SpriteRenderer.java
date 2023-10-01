package com.greentree.engine.moon.render.mesh;

import com.greentree.commons.graphics.smart.texture.Texture;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

import java.util.Objects;

@RequiredComponent({Transform.class})
public record SpriteRenderer(Asset<Texture> texture) implements ConstComponent {

    public SpriteRenderer {
        Objects.requireNonNull(texture);
    }

}
