package com.greentree.engine.moon.base.layer;

import com.greentree.engine.moon.base.property.world.CreateSceneProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class LayerSystem implements WorldInitSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder().require(Layer.class);

    @CreateSceneProperty({Layers.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        var layers = new Layers(BUILDER.build(world));
        sceneProperties.add(layers);
    }

}
