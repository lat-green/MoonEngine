package com.greentree.engine.moon.render;

import com.greentree.commons.graphics.smart.shader.material.Material;
import com.greentree.commons.graphics.smart.target.RenderCommandBuffer;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.render.camera.CameraTarget;
import com.greentree.engine.moon.render.camera.Cameras;
import com.greentree.engine.moon.render.mesh.MeshUtil;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;
import com.greentree.engine.moon.render.window.Window;
import com.greentree.engine.moon.render.window.WindowProperty;

public final class RenderMainCamera implements WorldInitSystem, UpdateSystem, DestroySystem {

    private Window window;
    private RenderCommandBuffer buffer;
    private Material material;
    private Cameras cameras;

    @Override
    public void destroy() {
        buffer.clear();
        buffer = null;
        material = null;
        window = null;
        cameras = null;
    }

    @ReadProperty({WindowProperty.class, Cameras.class, RenderLibraryProperty.class})
    @ReadComponent(CameraTarget.class)
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        window = sceneProperties.get(WindowProperty.class).window();
        var library = sceneProperties.get(RenderLibraryProperty.class).library();
        cameras = sceneProperties.get(Cameras.class);
        final var rmesh = library.build(MeshUtil.QUAD);
        final var shader = library.build(MaterialUtil.getDefaultTextureShader());
        material = shader.newMaterial();
        buffer = window.screanRenderTarget().buffer();
        buffer.bindMesh(rmesh);
        buffer.bindMaterial(material);
        buffer.draw();
    }

    @WriteProperty({RenderLibraryProperty.class, WindowProperty.class})
    @ReadProperty({Cameras.class})
    @ReadComponent(CameraTarget.class)
    @Override
    public void update() {
        window.swapBuffer();
        var camera = cameras.main();
        if (camera.contains(CameraTarget.class))
            material.put("render_texture", camera.get(CameraTarget.class).target().getColorTexture());
        buffer.execute();
    }

}
