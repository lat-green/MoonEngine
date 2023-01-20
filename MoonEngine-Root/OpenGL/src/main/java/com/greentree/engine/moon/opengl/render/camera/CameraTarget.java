package com.greentree.engine.moon.opengl.render.camera;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.Component;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer;
import com.greentree.common.graphics.sgl.freambuffer.FreamBufferBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.window.Window;
import com.greentree.engine.moon.render.camera.CameraComponent;

@RequiredComponent({CameraComponent.class})
public final class CameraTarget implements Component {
	
	private static final long serialVersionUID = 1L;
	
	private transient FreamBuffer buffer;
	
	public CameraTarget(int width, int height) {
		this(width, height, GLPixelFormat.RGB);
	}
	
	public CameraTarget(int width, int height, GLPixelFormat format) {
		this(create(width, height, format));
	}
	
	private static FreamBuffer create(int width, int height, GLPixelFormat format) {
		final var tex = new GLTexture2DImpl();
		final var buffer = new FreamBufferBuilder().addColor(tex, format).addDepth().build(width, height);
		tex.bind();
		tex.setFilter(GLFiltering.NEAREST);
		tex.unbind();
		return buffer;
	}
	
	public CameraTarget(Window window) {
		this(window.getWidth(), window.getHeight());
	}
	
	public CameraTarget(Window window, GLPixelFormat format) {
		this(window.getWidth(), window.getHeight());
	}
	
	private CameraTarget(FreamBuffer buffer) {
		Objects.requireNonNull(buffer);
		this.buffer = buffer;
	}
	
	public FreamBuffer buffer() {
		return buffer;
	}
	
	@Override
	public Component copy() {
		return new CameraTarget(buffer.getWidth(), buffer.getHeight());
	}
	
	@java.io.Serial
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		final var width = s.readInt();
		final var height = s.readInt();
		final var format = (GLPixelFormat) s.readObject();
		this.buffer = create(width, height, format);
	}
	
	@java.io.Serial
	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(buffer.getWidth());
		s.writeInt(buffer.getHeight());
		s.writeObject(buffer.getColor(0).getInternalformat());
	}
	
}
