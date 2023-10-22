package com.greentree.engine.moon.editor;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.layer.LayerSystem;
import com.greentree.engine.moon.base.name.NameSystem;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.base.time.TimeSystem;
import com.greentree.engine.moon.base.transform.RectTransform;
import com.greentree.engine.moon.base.transform.RectTransformUpdate;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.scene.SimpleScene;
import com.greentree.engine.moon.ecs.scene.WorldProperty;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.ecs.system.debug.DebugSystems;
import com.greentree.engine.moon.ecs.system.debug.PrintStreamSystemsProfiler;
import com.greentree.engine.moon.editor.ui.Button;
import com.greentree.engine.moon.editor.ui.ButtonSystem;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.render.camera.CameraSystem;
import com.greentree.engine.moon.signals.ui.ClickSystem;
import com.greentree.engine.moon.signals.ui.Clickable;
import com.greentree.engine.moon.signals.ui.ClickableClickSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class InitSceneModule implements LaunchModule {

    @WriteProperty({SceneManagerProperty.class})
    @ReadProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var scenes = context.get(SceneManagerProperty.class).manager();
        scenes.set(new SimpleScene() {

            @Override
            public void build(SceneProperties properties) {
                var world = properties.get(WorldProperty.class).world();
                final var b = world.newEntity();
                try (final var lock = b.lock()) {
                    lock.add(new Transform());
                    lock.add(new RectTransform(-.1f, -.1f, .1f, .1f, 0));
                    lock.add(new Button("Hello"));
                    lock.add(new Clickable());
                }
            }

            @Override
            public ECSSystem getSystems(Iterable<? extends ECSSystem> globalSystems) {
                final var systems = new ArrayList<ECSSystem>();
                for (var s : globalSystems)
                    systems.add(s);
                Collections.addAll(systems, new TimeSystem(), new CameraSystem(), new LayerSystem(),
                        new NameSystem(), new RectTransformUpdate(), new ButtonSystem(),
                        new ClickSystem(), new ClickableClickSystem());
                return new DebugSystems(new PrintStreamSystemsProfiler(
                        new File("log/systems_init.log"), new File("log/systems_update.log"),
                        new File("log/systems_destroy.log")), systems);
            }

        });

    }

}
