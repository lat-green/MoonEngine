package com.greentree.engine.moon.render.mesh;

import com.greentree.engine.moon.assets.asset.ConstAsset;
import com.greentree.engine.moon.base.component.CreateComponent;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.system.SimpleWorldInitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.MaterialUtil;
import com.greentree.engine.moon.render.material.Material;
import com.greentree.engine.moon.render.material.MaterialPropertiesBase;

public class SpriteMeshGenerator implements SimpleWorldInitSystem, UpdateSystem {

    private static final FilterBuilder SPRITE_MESHES = new FilterBuilder().require(SpriteRenderer.class)
            .ignore(MeshComponent.class);
    private static final FilterBuilder SPRITE_NO_MESH_RENDERS = new FilterBuilder().require(SpriteRenderer.class)
            .ignore(MeshRenderer.class);

    private Filter<? extends WorldEntity> sprite_meshes, sprite_renders;

    @Override
    public void init(World world) {
        sprite_meshes = SPRITE_MESHES.build(world);
        sprite_renders = SPRITE_NO_MESH_RENDERS.build(world);
    }

    @ReadComponent(SpriteRenderer.class)
    @CreateComponent({MeshRenderer.class, MeshComponent.class})
    @Override
    public void update() {
        for (var e : sprite_meshes) {
            final var mesh = new ConstAsset<>(MeshUtil.QUAD_SPRITE);
            final var c = new MeshComponent(mesh);
            e.add(c);
        }
        for (var e : sprite_renders) {
            final var m = new Material(MaterialUtil.getDefaultSpriteShader(), new MaterialPropertiesBase());
            m.properties().put("render_texture", e.get(SpriteRenderer.class).texture().getValue());
            final var c = new MeshRenderer(new ConstAsset<>(m));
            e.add(c);
        }
    }

}
