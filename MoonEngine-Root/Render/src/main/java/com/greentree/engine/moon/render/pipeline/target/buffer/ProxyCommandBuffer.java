package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;


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
	public void drawMesh(StaticMesh mesh, Shader material, MaterialProperties properties) {
		base.drawMesh(mesh, material, properties);
	}
	
	@Override
	public void drawSkyBox(Shader material, MaterialProperties properties) {
		base.drawSkyBox(material, properties);
	}
	
}
