package com.greentree.engine.moon.render.camera;

import com.greentree.commons.graphics.smart.texture.Texture;
import com.greentree.engine.moon.assets.Asset;
import com.greentree.engine.moon.ecs.component.ConstComponent;

public record SkyBoxComponent(Asset<Texture> texture) implements ConstComponent {

}
