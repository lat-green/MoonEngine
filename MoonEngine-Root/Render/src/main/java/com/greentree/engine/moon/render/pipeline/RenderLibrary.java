package com.greentree.engine.moon.render.pipeline;

import com.greentree.commons.graphics.smart.mesh.Mesh;
import com.greentree.commons.graphics.smart.shader.Shader;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

public interface RenderLibrary {

    RenderTargetTextuteBuilder createRenderTarget();

    Mesh build(StaticMesh quad);

    Shader build(ShaderProgramData quad);

}
