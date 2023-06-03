package com.greentree.engine.moon.replication.event;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.scene.SceneProperties;

import java.io.Serializable;

public interface EntityEvent extends Serializable {

    void accept(World world, SceneProperties properties);

}
