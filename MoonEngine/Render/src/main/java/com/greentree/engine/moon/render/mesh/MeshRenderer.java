package com.greentree.engine.moon.render.mesh;

import com.greentree.commons.graphics.smart.shader.material.Material;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({MeshComponent.class})
public record MeshRenderer(Asset<Material> material) implements ConstComponent {

}
