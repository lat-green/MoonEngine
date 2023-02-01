package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.Objects;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.camera.CameraComponent;
import com.greentree.engine.moon.render.camera.CameraUtil;
import com.greentree.engine.moon.render.material.Material;

public final class CameraCommandBuffer extends ProxyCommandBuffer {
	
	private final Entity camera;
	
	public CameraCommandBuffer(Entity camera, TargetCommandBuffer base) {
		super(base);
		Objects.requireNonNull(base);
		this.camera = camera;
	}
	
	@Override
	public void drawMesh(StaticMesh mesh, Material material) {
		final var view = CameraUtil.getView(camera);
		final var projection = camera.get(CameraComponent.class).getProjectionMatrix();
		final var veiwProjection = new Matrix4f().identity().mul(projection).mul(view);
		material.properties().put("projectionView", veiwProjection);
		super.drawMesh(mesh, material);
	}
	
	@Override
	public void drawSkyBox(Material material) {
		final var camera = this.camera.get(CameraComponent.class);
		Matrix4f veiwProjection;
		{
			float w = camera.width();
			float h = camera.height();
			float wh = w / h;
			veiwProjection = new Matrix4f().perspective(Mathf.PIHalf / wh, wh, 0, 1);
		}
		
		Matrix3f mat33 = new Matrix3f();
		Matrix4f view = CameraUtil.getView(this.camera);
		veiwProjection.mul(new Matrix4f(view.get3x3(mat33)));
		
		material.properties().put("projectionView", veiwProjection);
		
		super.drawSkyBox(material);
	}
	
}
