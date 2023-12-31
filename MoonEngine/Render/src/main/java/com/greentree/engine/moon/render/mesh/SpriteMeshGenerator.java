package com.greentree.engine.moon.render.mesh;

import com.greentree.commons.graphics.smart.mesh.Mesh;
import com.greentree.commons.graphics.smart.shader.Shader;
import com.greentree.engine.moon.assets.Asset;
import com.greentree.engine.moon.assets.serializator.manager.AssetManagerKt;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.component.CreateComponent;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.render.MaterialUtil;

public class SpriteMeshGenerator implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder SPRITE_MESHES = new FilterBuilder().require(SpriteRenderer.class)
            .ignore(MeshComponent.class);
    private static final FilterBuilder SPRITE_NO_MESH_RENDERS = new FilterBuilder().require(SpriteRenderer.class)
            .ignore(MeshRenderer.class);

    private Filter<? extends WorldEntity> sprite_meshes, sprite_renders;
    private Asset<Mesh> mesh;
    private Asset<Shader> shader;

    @ReadProperty(AssetManagerProperty.class)
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        var manager = sceneProperties.get(AssetManagerProperty.class).manager;
        mesh = AssetManagerKt.load(manager, Mesh.class, MeshUtil.QUAD_SPRITE);
        shader = AssetManagerKt.load(manager, Shader.class, MaterialUtil.getDefaultSpriteShader());
        sprite_meshes = SPRITE_MESHES.build(world);
        sprite_renders = SPRITE_NO_MESH_RENDERS.build(world);
    }

    @ReadComponent(SpriteRenderer.class)
    @CreateComponent({MeshRenderer.class, MeshComponent.class})
    @Override
    public void update() {
        for (var e : sprite_meshes) {
            final var c = new MeshComponent(mesh);
            e.add(c);
        }
        for (var e : sprite_renders) {
            final var c = new MeshRenderer(() -> {
                var m = shader.getValue().newMaterial();
                m.put("render_texture", e.get(SpriteRenderer.class).texture().getValue());
                return m;
            });
            e.add(c);
        }
    }

}
