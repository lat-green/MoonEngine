package com.greentree.engine.moon.render.mesh;

import com.greentree.commons.graphics.smart.texture.Texture;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

import java.io.Serial;
import java.util.Objects;

@RequiredComponent({Transform.class})
public final class SpriteRenderer implements ConstComponent {

    @Serial
    private static final long serialVersionUID = 0L;
    private final Asset<Texture> texture;

    public SpriteRenderer(Asset<Texture> texture) {
        Objects.requireNonNull(texture);
        this.texture = texture;
    }

    public Asset<Texture> texture() {
        return texture;
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SpriteRenderer) obj;
        return Objects.equals(this.texture, that.texture);
    }

    @Override
    public String toString() {
        return "SpriteRenderer[" +
                "texture=" + texture + ']';
    }

}
