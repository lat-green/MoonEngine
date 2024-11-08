package com.greentree.engine.moon.replication;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.integer.IntAction;
import com.greentree.engine.moon.ecs.scene.SceneProperty;

import java.util.function.IntConsumer;

public class Clients implements SceneProperty {

    private static final long serialVersionUID = 1L;

    private final IntAction action = new IntAction();

    public ListenerCloser addListener(IntConsumer listener) {
        return action.addListener(listener);
    }

    void event(int owner) {
        action.event(owner);
    }

}
