package com.greentree.engine.moon.render.mesh;

import com.greentree.commons.graphics.smart.mesh.Mesh;
import com.greentree.engine.moon.assets.Asset;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public record MeshComponent(Asset<? extends Mesh> mesh) implements ConstComponent {

    @Override
    public String toString() {
        String builder = "MeshComponent [" +
                mesh +
                "]";
        return builder;
    }

}
