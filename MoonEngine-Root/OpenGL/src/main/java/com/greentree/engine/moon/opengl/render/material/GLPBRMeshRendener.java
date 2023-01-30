package com.greentree.engine.moon.opengl.render.material;

import java.util.Objects;

import com.greentree.commons.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.mesh.MeshComponent;

@RequiredComponent({MeshComponent.class})
public class GLPBRMeshRendener implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	
	public final ValueProvider<GLPBRMaterial> material;
	
	public GLPBRMeshRendener(ValueProvider<GLPBRMaterial> material) {
		this.material = material;
	}
	
	@Override
	public void close() {
		material.close();
	}
	
	@Override
	public String toString() {
		return "GLPBRMeshRendener [" + material + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(material);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		GLPBRMeshRendener other = (GLPBRMeshRendener) obj;
		return Objects.equals(material, other.material);
	}
	
}
