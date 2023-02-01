package com.greentree.engine.moon.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.buffer.IntStaticDrawElementArrayBuffer;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.enums.gl.GLShaderType;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer;
import com.greentree.common.graphics.sgl.freambuffer.FreamBufferBuilder;
import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;
import com.greentree.common.graphics.sgl.vao.GLVertexArray;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.common.graphics.sgl.window.GLFWContext;
import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.util.array.FloatArray;
import com.greentree.commons.util.array.IntArray;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.mesh.GraphicsMesh;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.opengl.base.GLRenderTargetTextuteBuilder;
import com.greentree.engine.moon.opengl.base.OpenGLCommandBuffer;
import com.greentree.engine.moon.opengl.command.OpenGLContext;
import com.greentree.engine.moon.render.material.Material;
import com.greentree.engine.moon.render.material.MaterialProperty;
import com.greentree.engine.moon.render.pipeline.RenderLibraryContext;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderProgram;
import com.greentree.engine.moon.render.shader.data.ShaderProgramData;
import com.greentree.engine.moon.render.texture.CubeImageData;
import com.greentree.engine.moon.render.texture.CubeTextureData;

public final class GLRenderContext implements RenderLibraryContext, OpenGLContext, AutoCloseable {
	
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
	private final GLShaderProgram ShadowShader, CubeMapShadowShader, SkyBoxShader;
	
	private final Map<StaticMesh, GLVertexArray> vaos = new HashMap<>();
	private final GLVertexArray quadVAO, boxVAO;
	
	private final Map<FloatArray, FloatStaticDrawArrayBuffer> vbos = new HashMap<>();
	
	private final GLFWContext window;
	
	private Map<CubeTextureData, GLCubeMapTexture> cubeTextures = new HashMap<>();
	
	public GLRenderContext(GLFWContext context) {
		window = context;
		try {
			final GLSLShader SkyBoxShaderVert, SkyBoxShaderFrag;
			try(final var res = GLRenderContext.class.getClassLoader()
					.getResourceAsStream("shaders/skybox/skybox.vert")) {
				SkyBoxShaderVert = new GLSLShader(res, GLShaderType.VERTEX);
			}
			try(final var res = GLRenderContext.class.getClassLoader()
					.getResourceAsStream("shaders/skybox/skybox.frag")) {
				SkyBoxShaderFrag = new GLSLShader(res, GLShaderType.FRAGMENT);
			}
			final GLSLShader CubeMapShadowShaderVert, CubeMapShadowShaderFrag,
					CubeMapShadowShaderGeom;
			try(final var res = GLRenderContext.class.getClassLoader()
					.getResourceAsStream("shaders/shadow/point/point_shadow_mapping_depth.vert")) {
				CubeMapShadowShaderVert = new GLSLShader(res, GLShaderType.VERTEX);
			}
			try(final var res = GLRenderContext.class.getClassLoader()
					.getResourceAsStream("shaders/shadow/point/point_shadow_mapping_depth.frag")) {
				CubeMapShadowShaderFrag = new GLSLShader(res, GLShaderType.FRAGMENT);
			}
			try(final var res = GLRenderContext.class.getClassLoader()
					.getResourceAsStream("shaders/shadow/point/point_shadow_mapping_depth.geom")) {
				CubeMapShadowShaderGeom = new GLSLShader(res, GLShaderType.GEOMETRY);
			}
			
			SkyBoxShader = new GLShaderProgram(
					IteratorUtil.iterable(SkyBoxShaderVert, SkyBoxShaderFrag));
			SkyBoxShaderVert.close();
			SkyBoxShaderFrag.close();
			
			CubeMapShadowShader = new GLShaderProgram(IteratorUtil.iterable(CubeMapShadowShaderVert,
					CubeMapShadowShaderFrag, CubeMapShadowShaderGeom));
			CubeMapShadowShaderVert.close();
			CubeMapShadowShaderFrag.close();
			CubeMapShadowShaderGeom.close();
			
			//		ShadowShader = manager.loadAsync(GLShaderProgram.class, "shaders/shadow/direction/direction_shadow_mapping_depth.glsl");
			//		CubeMapShadowShader = manager.loadAsync(GLShaderProgram.class, "shaders/shadow/point/point_shadow_mapping_depth.glsl");
			//		SkyBoxShader = manager.loadAsync(GLShaderProgram.class, "skybox.glsl");
			
			ShadowShader = null;
			
			final var quadVBO = getVBO(new FloatArray(quadVertices));
			
			final var boxEBO = getEBO(new IntArray(elements));
			final var boxVBO = getVBO(new FloatArray(vertices));
			
			quadVAO = new GLVertexArray(AttributeGroup.of(quadVBO, 2));
			boxVAO = new GLVertexArray(boxEBO, AttributeGroup.of(boxVBO, 3));
		}catch(Exception e) {
			throw new WrappedException(e);
		}
	}
	
	private static FreamBuffer create(int width, int height, GLPixelFormat format) {
		final var tex = new GLTexture2DImpl();
		final var buffer = new FreamBufferBuilder().addColor(tex, format).addDepth().build(width,
				height);
		tex.bind();
		tex.setFilter(GLFiltering.NEAREST);
		tex.unbind();
		return buffer;
	}
	
	@Override
	public TargetCommandBuffer buffer() {
		return new OpenGLCommandBuffer(this);
	}
	
	@Override
	public MaterialProperty build(CubeImageData image) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public MaterialProperty build(ImageData image) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ShaderProgram build(ShaderProgramData program) {
		System.out.println(program);
		return null;
	}
	
	@Override
	public void close() {
		boxVAO.close();
		quadVAO.close();
		ShadowShader.close();
		CubeMapShadowShader.close();
		SkyBoxShader.close();
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
	public Material getDefaultCubeMapShadowMaterial() {
		return new Material(new OpenGLShaderProgramAddpter(CubeMapShadowShader));
	}
	
	@Override
	public Material getDefaultShadowMaterial() {
		return new Material(new OpenGLShaderProgramAddpter(ShadowShader));
	}
	
	
	@Override
	public Material getDefaultSpriteMaterial() {
		return new Material(new SpriteMaterial());
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
	
}
