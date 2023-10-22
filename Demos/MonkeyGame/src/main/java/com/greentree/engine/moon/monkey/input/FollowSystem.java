package com.greentree.engine.moon.monkey.input;

import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class FollowSystem implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder()
            .require(Follow.class);

    private Filter<?> filter;
    private Time time;

    @Override
    public void update() {
        for (var e : filter) {
            var my_t = e.get(Transform.class).position;
            for (var target : e.get(Follow.class).targets()) {
                var target_t = target.get(Transform.class).position;
                var speed = e.get(Follow.class).speed();
                if (my_t.x() < target_t.x()) {
                    var t = Mathf.lerp(my_t.x(), target_t.x(), speed * time.delta());
                    my_t.x(t);
                }
            }
        }
    }

    @ReadProperty({Time.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        filter = BUILDER.build(world);
        time = sceneProperties.get(Time.class);
    }

}
