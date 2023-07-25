package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.material.Property;

public record SkyBoxComponent(Asset<Property> texture) implements ConstComponent {

}
