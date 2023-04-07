package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.opengl.GL30.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;
import com.greentree.common.graphics.sgl.vao.GLVertexArray;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.opengl.adapter.buffer.PushCommandBuffer;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.material.Property;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderData;
import com.greentree.engine.moon.render.shader.ShaderProgramData;
import com.greentree.engine.moon.render.texture.CubeTextureData;
import com.greentree.engine.moon.render.texture.Texture2DData;

public final class GLRenderLibrary implements RenderLibrary, RenderTarget {
	
	private static final RenderMeshBuilder BUILDER = new RenderMeshBuilder();
	
	private final Map<CubeTextureData, GLTexture> cubeTextures = new HashMap<>();
	private final Map<Texture2DData, GLTexture> textures = new HashMap<>();
	
	@Override
	public TargetCommandBuffer buffer() {
		return new PushCommandBuffer(this);
	}
	
	@Override
	public Property build(CubeTextureData texture) {
		final var gl = toGLTexture(texture);
		return new TextureAddapter(gl);
	}
	
	@Override
	public Property build(Texture2DData texture) {
		final var gl = toGLTexture(texture);
		return new TextureAddapter(gl);
	}
	
	@Override
	public RenderTargetTextuteBuilder createRenderTarget() {
		return new GLRenderTargetTextuteBuilder(this);
	}
	
	public GLVertexArray getVAO(AttributeData mesh) {
		return BUILDER.getVAO(mesh);
	}
	
	private GLTexture toGLTexture(CubeTextureData texture) {
		for(var e : cubeTextures.entrySet())
			if(e.getKey() == texture)
				return e.getValue();
			
		final var tex = new GLCubeMapTexture();
		cubeTextures.put(texture, tex);
		tex.bind();
		
		final var iter = texture.image().iterator();
		for(var d : tex.dirs()) {
			final var image = iter.next();
			d.setData(GLPixelFormat.RGB, image.getWidth(), image.getHeight(), GLPixelFormat.gl(image.getFormat()),
					image.getByteBuffer());
		}
		
		final var type = texture.type();
		
		tex.setMagFilter(GLEnums.get(type.filteringMag()));
		tex.setMagFilter(GLEnums.get(type.filteringMin()));
		tex.setWrapX(GLEnums.get(type.wrapX()));
		tex.setWrapY(GLEnums.get(type.wrapY()));
		tex.setWrapZ(GLEnums.get(type.wrapZ()));
		
		glGenerateMipmap(GLCubeMapTexture.GL_TEXTURE_TARGET.glEnum);
		tex.unbind();
		return tex;
	}
	
	private GLTexture toGLTexture(Texture2DData texture) {
		for(var e : textures.entrySet())
			if(e.getKey() == texture)
				return e.getValue();
			
		final var tex = new GLTexture2DImpl();
		textures.put(texture, tex);
		tex.bind();
		
		final var image = texture.image();
		tex.setData(GLPixelFormat.RGB, image.getWidth(), image.getHeight(), GLPixelFormat.gl(image.getFormat()),
				image.getByteBuffer());
		
		final var type = texture.type();
		
		tex.setMagFilter(GLEnums.get(type.filteringMag()));
		tex.setMagFilter(GLEnums.get(type.filteringMin()));
		tex.setWrapX(GLEnums.get(type.wrapX()));
		tex.setWrapY(GLEnums.get(type.wrapY()));
		
		glGenerateMipmap(GLTexture2DImpl.GL_TEXTURE_TARGET.glEnum);
		tex.unbind();
		return tex;
	}
	
	public record TArray<T>(T[] array) {
		
		@Override
		public int hashCode() {
			final var prime = 31;
			var result = 1;
			result = prime * result + Arrays.deepHashCode(array);
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(!(obj instanceof TArray))
				return false;
			TArray<?> other = (TArray<?>) obj;
			return Arrays.deepEquals(array, other.array);
		}
		
	}
	
	private static final class RenderMeshBuilder {
		
		private final Map<AttributeData, GLVertexArray> vaos = new HashMap<>();
		
		public GLVertexArray getVAO(AttributeData g) {
			if(vaos.containsKey(g))
				return vaos.get(g);
			final var vbo = getVBO(g.vertex());
			final var vao = new GLVertexArray(AttributeGroup.of(vbo, g.sizes()));
			vaos.put(g, vao);
			vbo.close();
			return vao;
		}
		
		public static FloatStaticDrawArrayBuffer getVBO(float[] VERTEXS) {
			final var vbo = new FloatStaticDrawArrayBuffer();
			vbo.bind();
			try(final var stack = MemoryStack.create(VERTEXS.length * GLType.FLOAT.size).push()) {
				vbo.setData(stack.floats(VERTEXS));
			}
			vbo.unbind();
			return vbo;
		}
		
	}
	
	public RenderMesh build(StaticMesh mesh) {
		return build(mesh.getAttributeGroup(TargetCommandBuffer.COMPONENTS));
	}
	
	public RenderMesh build(AttributeData mesh) {
		final var vao = getVAO(mesh);
		return new VAORenderMeshAddapter(vao);
	}
	
	private GLSLShader build(ShaderData shader) {
		final var s = new GLSLShader(shader.text(), GLEnums.get(shader.type()));
		return s;
	}
	
	public Shader build(ShaderProgramData program) {
		final var vert = build(program.vert());
		final var frag = build(program.frag());
		if(program.geom() != null) {
			final var geom = build(program.geom());
			final var p = new GLShaderProgram(IteratorUtil.iterable(vert, frag, geom));
			vert.close();
			frag.close();
			geom.close();
			return new ShaderAddapter(p);
		}
		final var p = new GLShaderProgram(IteratorUtil.iterable(vert, frag));
		vert.close();
		frag.close();
		return new ShaderAddapter(p);
	}
	
}
