package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;


public class ProxyCommandBuffer implements TargetCommandBuffer {
	
	protected final TargetCommandBuffer base;
	
	protected ProxyCommandBuffer(TargetCommandBuffer base) {
		Objects.requireNonNull(base);
		this.base = base;
	}
	
	@Override
	public void clearRenderTargetColor(Color color) {
		base.clearRenderTargetColor(color);
	}
	
	@Override
	public void clearRenderTargetDepth(float depth) {
		base.clearRenderTargetDepth(depth);
	}
	
	@Override
	public void close() {
		base.close();
	}
	
	@Override
	public void drawMesh(RenderMesh mesh, Shader material, MaterialProperties properties) {
		base.drawMesh(mesh, material, properties);
	}
	
	@Override
	public void drawSkyBox(Shader material, MaterialProperties properties) {
		base.drawSkyBox(material, properties);
	}
	
}
