package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.base.name.Names;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

import java.util.ArrayList;

public class BananaDeleteSystem implements WorldInitSystem, UpdateSystem {

    private Filter<?> filter;
    private WorldEntity camera;

    @WriteComponent(Transform.class)
    @Override
    public void update() {
        var toDelete = new ArrayList<WorldEntity>();
        var minX = camera.get(Transform.class).position.x() - 1.2;
        for (var e : filter) {
            var x = e.get(Transform.class).position.x();
            if (x < minX)
                toDelete.add(e);
        }
        for (var e : toDelete)
            e.delete();
    }

    @ReadProperty(Names.class)
    @Override
    public void init(World world, SceneProperties properties) {
        camera = properties.get(Names.class).get("camera");
        filter = world.newFilterBuilder().require(Banana.class).build();
    }

}
