package com.greentree.engine.moon.render.scene;

import org.joml.Matrix4f;

import com.greentree.common.renderer.material.Material;
import com.greentree.common.renderer.mesh.GraphicsMesh;
import com.greentree.common.renderer.scene.Model;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.MeshRenderer;
import com.greentree.engine.moon.render.mesh.MeshComponent;


public record MoonModel(Entity entity) implements Model {
	
	@Override
	public Matrix4f model() {
		return entity.get(Transform.class).getModelMatrix();
	}
	
	@Override
	public GraphicsMesh mesh() {
		return entity.get(MeshComponent.class).mesh().get();
	}
	
	@Override
	public Material material() {
		return entity.get(MeshRenderer.class).material().get();
	}
	
}
