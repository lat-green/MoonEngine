package com.greentree.engine.moon.render;

import com.greentree.commons.util.function.SaveFunction;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.shader.ShaderDataImpl;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderProgramData;
import com.greentree.engine.moon.render.shader.ShaderProgramDataImpl;
import com.greentree.engine.moon.render.shader.ShaderType;

public class MaterialUtil {
	
	
	private static final Instances INSTANCES = new Instances();
	private static final ShaderProgramData DefaultCubeMapShadowShaderData;
	private static final ShaderProgramData DefaultShadowShaderData;
	private static final ShaderProgramData DefaultSkyBoxShderData;
	private static final ShaderProgramData DefaultSpriteShaderData;
	private static final String DefaultSprite_VERTEX_NAME = "shaders/texture/texture.vert";
	
	private static final String DefaultSprite_FRAGMENT_NAME = "shaders/texture/texture.frag";
	private static final String DefaultCubeMapShadow_VERTEX_NAME = "shaders/shadow/point/point_shadow_mapping_depth.vert";
	
	private static final String DefaultCubeMapShadow_GEOMETRY_NAME = "shaders/shadow/point/point_shadow_mapping_depth.geom";
	private static final String DefaultCubeMapShadow_FRAGMENT_NAME = "shaders/shadow/point/point_shadow_mapping_depth.frag";
	private static final String DefaultShadow_VERTEX_NAME = "shaders/shadow/direction/direction_shadow_mapping_depth.vert";
	
	private static final String DefaultShadow_FRAGMENT_NAME = "shaders/shadow/direction/direction_shadow_mapping_depth.frag";
	private static final String DefaultSkyBox_VERTEX_NAME = "shaders/skybox/skybox.vert";
	
	private static final String DefaultSkyBox_FRAGMENT_NAME = "shaders/skybox/skybox.frag";
	static {
		final var vert = new ShaderDataImpl(DefaultCubeMapShadow_VERTEX(), ShaderType.VERTEX,
				ShaderLanguage.GLSL);
		final var geom = new ShaderDataImpl(DefaultCubeMapShadow_GEOMETRY(), ShaderType.GEOMETRY,
				ShaderLanguage.GLSL);
		final var frag = new ShaderDataImpl(DefaultCubeMapShadow_FRAGMENT(), ShaderType.FRAGMENT,
				ShaderLanguage.GLSL);
		DefaultCubeMapShadowShaderData = new ShaderProgramDataImpl(vert, frag, geom);
	}
	
	
	static {
		final var vert = new ShaderDataImpl(DefaultShadow_VERTEX(), ShaderType.VERTEX,
				ShaderLanguage.GLSL);
		final var frag = new ShaderDataImpl(DefaultShadow_FRAGMENT(), ShaderType.FRAGMENT,
				ShaderLanguage.GLSL);
		DefaultShadowShaderData = new ShaderProgramDataImpl(vert, frag);
	}
	static {
		final var vert = new ShaderDataImpl(DefaultSkyBox_VERTEX(), ShaderType.VERTEX,
				ShaderLanguage.GLSL);
		final var frag = new ShaderDataImpl(DefaultSkyBox_FRAGMENT(), ShaderType.FRAGMENT,
				ShaderLanguage.GLSL);
		DefaultSkyBoxShderData = new ShaderProgramDataImpl(vert, frag);
	}
	static {
		final var vert = new ShaderDataImpl(DefaultSprite_VERTEX(), ShaderType.VERTEX,
				ShaderLanguage.GLSL);
		final var frag = new ShaderDataImpl(DefaultSprite_FRAGMENT(), ShaderType.FRAGMENT,
				ShaderLanguage.GLSL);
		DefaultSpriteShaderData = new ShaderProgramDataImpl(vert, frag);
	}
	
	private final Shader DefaultCubeMapShadowShader;
	private final Shader DefaultShadowShader;
	private final Shader DefaultSkyBoxShder;
	private final Shader DefaultSpriteShader;
	
	private MaterialUtil(RenderLibrary library) {
		DefaultSpriteShader = library.build(DefaultSpriteShaderData);
		DefaultCubeMapShadowShader = library.build(DefaultCubeMapShadowShaderData);
		DefaultShadowShader = library.build(DefaultShadowShaderData);
		DefaultSkyBoxShder = library.build(DefaultSkyBoxShderData);
	}
	
	public static MaterialUtil get(RenderLibrary library) {
		return INSTANCES.apply(library);
	}
	
	public static Shader getDefaultCubeMapShadowShader(RenderLibrary library) {
		return get(library).getDefaultCubeMapShadowShader();
	}
	
	public static Shader getDefaultShadowShader(RenderLibrary library) {
		return get(library).getDefaultShadowShader();
	}
	
	public static Shader getDefaultSkyBoxShader(RenderLibrary library) {
		return get(library).getDefaultSkyBoxShader();
	}
	
	public static Shader getDefaultSpriteShader(RenderLibrary library) {
		return get(library).getDefaultSpriteShader();
	}
	
	private static String DefaultCubeMapShadow_FRAGMENT() {
		return text(DefaultCubeMapShadow_FRAGMENT_NAME);
	}
	
	private static String DefaultCubeMapShadow_GEOMETRY() {
		return text(DefaultCubeMapShadow_GEOMETRY_NAME);
	}
	
	private static String DefaultCubeMapShadow_VERTEX() {
		return text(DefaultCubeMapShadow_VERTEX_NAME);
	}
	
	private static String DefaultShadow_FRAGMENT() {
		return text(DefaultShadow_FRAGMENT_NAME);
	}
	
	private static String DefaultShadow_VERTEX() {
		return text(DefaultShadow_VERTEX_NAME);
	}
	
	private static String DefaultSkyBox_FRAGMENT() {
		return text(DefaultSkyBox_FRAGMENT_NAME);
	}
	
	private static String DefaultSkyBox_VERTEX() {
		return text(DefaultSkyBox_VERTEX_NAME);
	}
	
	private static String DefaultSprite_FRAGMENT() {
		return text(DefaultSprite_FRAGMENT_NAME);
	}
	
	private static String DefaultSprite_VERTEX() {
		return text(DefaultSprite_VERTEX_NAME);
	}
	
	private static String text(String name) {
		try(final var stream = MaterialUtil.class.getClassLoader().getResourceAsStream(name)) {
			return new String(stream.readAllBytes());
		}catch(Exception e) {
			throw new RuntimeException("name:" + name, e);
		}
	}
	
	public Shader getDefaultCubeMapShadowShader() {
		return DefaultCubeMapShadowShader;
	}
	
	public Shader getDefaultShadowShader() {
		return DefaultShadowShader;
	}
	
	public Shader getDefaultSkyBoxShader() {
		return DefaultSkyBoxShder;
	}
	
	public Shader getDefaultSpriteShader() {
		return DefaultSpriteShader;
	}
	
	private static final class Instances extends SaveFunction<RenderLibrary, MaterialUtil> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		protected MaterialUtil create(RenderLibrary library) {
			return new MaterialUtil(library);
		}
		
	}
	
}
