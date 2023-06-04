package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.base.property.world.CreateSceneProperty;
import com.greentree.engine.moon.base.property.world.WriteSceneProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CameraSystem implements WorldInitSystem, UpdateSystem {

    private static final Logger LOG = LogManager.getLogger(Cameras.class);

    private static final FilterBuilder CAMERAS = new FilterBuilder().require(CameraComponent.class);
    private Cameras cameras;
    private Filter<? extends WorldEntity> camerasFilter;

    @CreateSceneProperty({Cameras.class})
    public void init(World world, SceneProperties properties) {
        camerasFilter = CAMERAS.build(world);
        cameras = new Cameras();
        properties.add(cameras);
    }

    @WriteComponent({CameraComponent.class})
    @WriteSceneProperty(Cameras.class)
    @Override
    public void update() {
        var iter = camerasFilter.iterator();
        if (iter.hasNext())
            cameras.setMainCamera(iter.next());
    }

}
