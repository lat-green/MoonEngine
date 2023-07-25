package com.greentree.engine.moon.render.mesh;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.material.Material;

import java.util.Objects;

@RequiredComponent({MeshComponent.class})
public record MeshRenderer(Asset<Material> material) implements ConstComponent {

    public MeshRenderer {
        Objects.requireNonNull(material);
    }

}
