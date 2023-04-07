package com.greentree.engine.moon.render.pipeline.target.buffer;

import java.util.Objects;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.render.camera.CameraComponent;
import com.greentree.engine.moon.render.camera.CameraUtil;
import com.greentree.engine.moon.render.mesh.MeshUtil;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.pipeline.material.Property;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

public final class CameraCommandBuffer extends ProxyCommandBuffer {
	
	private final Entity camera;
	
	public CameraCommandBuffer(Entity camera, TargetCommandBuffer base) {
		super(base);
		Objects.requireNonNull(camera);
		this.camera = camera;
	}
	
	@Override
	public void drawMesh(AttributeData mesh, ShaderProgramData shader, MaterialProperties properties) {
		final var transform = this.camera.get(Transform.class);
		final var view = CameraUtil.getView(camera);
		final var projection = camera.get(CameraComponent.class).getProjectionMatrix();
		final var veiwProjection = new Matrix4f().identity().mul(projection).mul(view);
		properties.put("projectionView", veiwProjection);
		properties.put("viewPos", transform.position);
		super.drawMesh(mesh, shader, properties);
	}
	
	public void drawSkyBox(ShaderProgramData shader, Property texture) {
		final var transform = this.camera.get(Transform.class);
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
		
		final var properties = new MaterialPropertiesBase();
		properties.put("skybox", texture);
		
		properties.put("projectionView", veiwProjection);
		properties.put("viewPos", transform.position);
		
		final var BOX = MeshUtil.BOX;
		super.drawMesh(BOX, shader, properties);
	}
	
}
