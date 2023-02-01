package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.material.Material;


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
	public void drawMesh(StaticMesh mesh, Material material) {
		base.drawMesh(mesh, material);
	}
	
	@Override
	public void drawSkyBox(Material material) {
		base.drawSkyBox(material);
	}
	
}
