package com.greentree.engine.moon.render.scene;

import java.util.Objects;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.common.renderer.texture.CubeTextureData;
import com.greentree.commons.assets.value.Value;
import com.greentree.engine.moon.render.camera.CameraComponent;

@RequiredComponent({CameraComponent.class})
public class SkyBoxComponent implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	
	public final Value<CubeTextureData> texture;
	
	public SkyBoxComponent(Value<CubeTextureData> texture) {
		this.texture = texture;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (getClass() != obj.getClass()))
			return false;
		SkyBoxComponent other = (SkyBoxComponent) obj;
		return Objects.equals(texture, other.texture);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(texture);
	}
	
	
}
