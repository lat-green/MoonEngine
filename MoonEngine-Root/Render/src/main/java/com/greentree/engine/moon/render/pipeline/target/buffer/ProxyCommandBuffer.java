package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.shader.ShaderProgramData;


public class ProxyCommandBuffer implements TargetCommandBuffer {
	
	protected final TargetCommandBuffer base;
	
	protected ProxyCommandBuffer(TargetCommandBuffer base) {
		Objects.requireNonNull(base);
		this.base = base;
	}
	
	@Override
	public void clear() {
		base.clear();
	}
	
	@Override
	public void clearColor(Color color) {
		base.clearColor(color);
	}
	
	@Override
	public void clearDepth(float depth) {
		base.clearDepth(depth);
	}
	
	@Override
	public void disableCullFace() {
		base.disableCullFace();
	}
	
	@Override
	public void disableDepthTest() {
		base.disableDepthTest();
	}
	
	@Override
	public void drawMesh(AttributeData mesh, ShaderProgramData shader, MaterialProperties properties) {
		base.drawMesh(mesh, shader, properties);
	}
	
	@Override
	public void enableCullFace() {
		base.enableCullFace();
	}
	
	@Override
	public void enableDepthTest() {
		base.enableDepthTest();
	}
	
	@Override
	public void execute() {
		base.execute();
	}
	
}
