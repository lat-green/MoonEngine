package com.greentree.engine.moon.render.mesh;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.mesh.StaticMesh;

import java.util.Objects;

@RequiredComponent({Transform.class})
public record MeshComponent(Asset<? extends StaticMesh> mesh) implements ConstComponent {

    public MeshComponent {
        Objects.requireNonNull(mesh);
    }

    @Override
    public String toString() {
		String builder = "MeshComponent [" +
				mesh +
				"]";
        return builder;
    }

}
