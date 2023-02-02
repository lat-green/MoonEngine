package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.buffer.IntStaticDrawElementArrayBuffer;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;
import com.greentree.common.graphics.sgl.vao.GLVertexArray;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.common.graphics.sgl.window.GLFWContext;
import com.greentree.commons.image.Color;
import com.greentree.commons.util.array.FloatArray;
import com.greentree.commons.util.array.IntArray;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.opengl.base.GLRenderTargetTextuteBuilder;
import com.greentree.engine.moon.opengl.base.OpenGLCommandBuffer;
import com.greentree.engine.moon.opengl.command.OpenGLContext;
import com.greentree.engine.moon.opengl.material.OpenGLMaterialProperties;
import com.greentree.engine.moon.opengl.material.OpenGLMaterialPropertiesImpl;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderData;
import com.greentree.engine.moon.render.shader.ShaderProgramData;
import com.greentree.engine.moon.render.texture.data.CubeTextureData;

public final class GLRenderLibrary implements RenderLibrary, OpenGLContext, AutoCloseable {
	
	private static final StaticMeshFaceComponent[] TYPES = new StaticMeshFaceComponent[]{
			StaticMeshFaceComponent.VERTEX,StaticMeshFaceComponent.NORMAL,
			StaticMeshFaceComponent.TEXTURE_COORDINAT,StaticMeshFaceComponent.TANGENT};
	
	private static final int elements[] = {0,7,1,6,2,5,3,4,0,7};
	private static final float vertices[] = {
			// positions
			-0.5f,-0.5f,-0.5f,0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f,-0.5f,-0.5f,0.5f,
			
			-0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,-0.5f,-0.5f,0.5f,-0.5f,};
	
	private static final float quadVertices[] = {
			// positions
			-1.0f,1.0f,-1.0f,-1.0f,1.0f,1.0f,1.0f,-1.0f,};
	
	private final Map<StaticMesh, GLVertexArray> vaos = new HashMap<>();
	private final GLVertexArray quadVAO, boxVAO;
	
	private final Map<FloatArray, FloatStaticDrawArrayBuffer> vbos = new HashMap<>();
	
	private final GLFWContext window;
	
	private Map<CubeTextureData, GLCubeMapTexture> cubeTextures = new HashMap<>();
	
	public GLRenderLibrary(GLFWContext context) {
		window = context;
		try {
			final var quadVBO = getVBO(new FloatArray(quadVertices));
			
			final var boxEBO = getEBO(new IntArray(elements));
			final var boxVBO = getVBO(new FloatArray(vertices));
			
			quadVAO = new GLVertexArray(AttributeGroup.of(quadVBO, 2));
			boxVAO = new GLVertexArray(boxEBO, AttributeGroup.of(boxVBO, 3));
		}catch(Exception e) {
			throw new WrappedException(e);
		}
	}
	
	@Override
	public TargetCommandBuffer buffer() {
		return new OpenGLCommandBuffer(this);
	}
	
	
	@Override
	public Shader build(ShaderProgramData program) {
		final var vert = build(program.vert());
		final var frag = build(program.frag());
		if(program.geom() != null) {
			final var geom = build(program.geom());
			final var p = new GLShaderProgram(IteratorUtil.iterable(vert, frag, geom));
			return new OpenGLAdapterShader(p);
		}
		final var p = new GLShaderProgram(IteratorUtil.iterable(vert, frag));
		return new OpenGLAdapterShader(p);
	}
	
	@Override
	public void close() {
		boxVAO.close();
		quadVAO.close();
		for(var vao : vaos.values())
			vao.close();
		for(var vbo : vbos.values())
			vbo.close();
		vaos.clear();
		vbos.clear();
	}
	
	@Override
	public RenderTargetTextuteBuilder createRenderTarget() {
		return new GLRenderTargetTextuteBuilder(this);
	}
	
	@Override
	public GLVertexArray getVAO(StaticMesh mesh) {
		if(vaos.containsKey(mesh))
			return vaos.get(mesh);
		final var g = mesh.getAttributeGroup(TYPES);
		final var vbo = getVBO(new FloatArray(g.vertex()));
		final var vao = new GLVertexArray(AttributeGroup.of(vbo, g.sizes()));
		vaos.put(mesh, vao);
		return vao;
	}
	
	public FloatStaticDrawArrayBuffer getVBO(FloatArray array) {
		if(vbos.containsKey(array))
			return vbos.get(array);
		final var VERTEXS = array.array;
		final var vbo = new FloatStaticDrawArrayBuffer();
		vbos.put(array, vbo);
		vbo.bind();
		try(final var stack = MemoryStack.create(VERTEXS.length * GLType.FLOAT.size).push()) {
			vbo.setData(stack.floats(VERTEXS));
		}
		vbo.unbind();
		return vbo;
	}
	
	@Override
	public OpenGLMaterialProperties newMaterialProperties() {
		return new OpenGLMaterialPropertiesImpl();
	}
	
	@Override
	public void renderBox() {
		boxVAO.bind();
		glDrawArrays(GLPrimitive.QUADS.glEnum, 0, 8);
		glDrawElements(GLPrimitive.QUAD_STRIP.glEnum, 10, GLType.UNSIGNED_INT.glEnum, 0);
		GLVertexArray.BINDER.unbind();
	}
	
	@Override
	public void renderQuad() {
		quadVAO.bind();
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
		GLVertexArray.BINDER.unbind();
	}
	
	@Override
	public void swapBuffer() {
		window.swapBuffer();
		glViewport(0, 0, window.getWidth(), window.getHeight());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT
				| GL_STENCIL_BUFFER_BIT);
	}
	
	private GLSLShader build(ShaderData shader) {
		final var s = new GLSLShader(shader.text(), GLEnums.get(shader.type()));
		return s;
	}
	
	private IntStaticDrawElementArrayBuffer getEBO(IntArray array) {
		//		if(vbos.containsKey(array))
		//			return vbos.get(array);
		final var VERTEXS = array.array;
		final var Ebo = new IntStaticDrawElementArrayBuffer();
		//		vbos.put(array, Ebo);
		Ebo.bind();
		try(final var stack = MemoryStack.create(VERTEXS.length * GLType.FLOAT.size).push()) {
			Ebo.setData(stack.ints(VERTEXS));
		}
		Ebo.unbind();
		return Ebo;
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
			d.setData(GLPixelFormat.RGB, image.getWidth(), image.getHeight(),
					GLPixelFormat.gl(image.getFormat()), image.getByteBuffer());
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
	
	@Override
	public void clearRenderTargetColor(Color color) {
		glClearColor(color.r, color.g, color.b, color.a);
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void clearRenderTargetDepth(float depth) {
		glClearDepth(depth);
		glClear(GL_DEPTH_BUFFER_BIT);
	}
	
}
