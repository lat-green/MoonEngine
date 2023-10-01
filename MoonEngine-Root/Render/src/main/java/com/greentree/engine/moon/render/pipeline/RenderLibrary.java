package com.greentree.engine.moon.render.pipeline;

import com.greentree.commons.graphics.smart.mesh.Mesh;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;

public interface RenderLibrary {

    RenderTargetTextuteBuilder createRenderTarget();

    Mesh build(StaticMesh quad);

}
