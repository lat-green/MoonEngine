package com.greentree.engine.moon.opengl.render.skybox;

import java.util.Objects;

import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.camera.CameraComponent;

@RequiredComponent({CameraComponent.class})
public class SkyBoxComponent0 implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	
	public final ValueProvider<GLCubeMapTexture> texture;
	
	public SkyBoxComponent0(ValueProvider<GLCubeMapTexture> texture) {
		this.texture = texture;
	}
	
	@Override
	public void close() {
		texture.close();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (getClass() != obj.getClass()))
			return false;
		SkyBoxComponent0 other = (SkyBoxComponent0) obj;
		return Objects.equals(texture, other.texture);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(texture);
	}
	
	
}
