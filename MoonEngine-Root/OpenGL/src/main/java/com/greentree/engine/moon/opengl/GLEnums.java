package com.greentree.engine.moon.opengl;

import java.util.function.Supplier;

import com.greentree.common.graphics.sgl.SGLFW.GLFWKey;
import com.greentree.common.graphics.sgl.SGLFW.GLFWMouseButton;
import com.greentree.common.graphics.sgl.enums.gl.GLShaderType;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLWrapping;
import com.greentree.engine.moon.render.shader.ShaderType;
import com.greentree.engine.moon.render.texture.Filtering;
import com.greentree.engine.moon.render.texture.Wrapping;
import com.greentree.engine.moon.render.window.callback.ButtonAction;
import com.greentree.engine.moon.render.window.callback.KeyMode;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.MouseButton;

public class GLEnums {
	
	public static GLShaderType get(ShaderType type) {
		return switch(type) {
			case VERTEX -> GLShaderType.VERTEX;
			case FRAGMENT -> GLShaderType.FRAGMENT;
			case GEOMETRY -> GLShaderType.GEOMETRY;
		};
	}
	
	public static GLFiltering get(Filtering filtering) {
		return switch(filtering) {
			case LINEAR -> GLFiltering.LINEAR;
			case NEAREST -> GLFiltering.NEAREST;
		
			default -> GLFiltering.valueOf(filtering.name());
		};
	}
	
	public static GLWrapping get(Wrapping wrap) {
		return switch(wrap) {
			case CLAMP_TO_BORDER -> GLWrapping.CLAMP_TO_BORDER;
			case CLAMP_TO_EDGE -> GLWrapping.CLAMP_TO_EDGE;
			case MIRRORED_REPEAT -> GLWrapping.MIRRORED_REPEAT;
			case REPEAT -> GLWrapping.REPEAT;
		};
	}
	
	public static Key get(GLFWKey key) {
		return Key.valueOf(key.name());
	}
	
	public static ButtonAction get(com.greentree.common.graphics.sgl.SGLFW.ButtonAction action) {
		return switch(action) {
			case PRESS -> ButtonAction.PRESS;
			case RELEASE -> ButtonAction.RELEASE;
			case REPEAT -> ButtonAction.REPEAT;
		};
	}
	
	
	public static KeyMode get(com.greentree.common.graphics.sgl.SGLFW.KeyMode action) {
		return switch(action) {
			case MOD_ALT -> KeyMode.MOD_ALT;
			case MOD_CAPS_LOCK -> KeyMode.MOD_CAPS_LOCK;
			case MOD_CONTROL -> KeyMode.MOD_CONTROL;
			case MOD_NUM_LOCK -> KeyMode.MOD_NUM_LOCK;
			case MOD_SHIFT -> KeyMode.MOD_SHIFT;
			case MOD_SUPER -> KeyMode.MOD_SUPER;
		};
	}
	
	public static Supplier<KeyMode[]> get(Supplier<com.greentree.common.graphics.sgl.SGLFW.KeyMode[]> mods) {
		return () -> get(mods.get());
	}
	
	public static KeyMode[] get(com.greentree.common.graphics.sgl.SGLFW.KeyMode[] mods) {
		var result = new KeyMode[mods.length];
		for(int i = 0; i < result.length; i++) {
			result[i] = get(mods[i]);
		}
		return result;
	}
	
	public static MouseButton get(GLFWMouseButton button) {
		return switch(button) {
			case MOUSE_BUTTON_LEFT -> MouseButton.MOUSE_BUTTON_LEFT;
			case MOUSE_BUTTON_MIDDLE -> MouseButton.MOUSE_BUTTON_MIDDLE;
			case MOUSE_BUTTON_RIGHT -> MouseButton.MOUSE_BUTTON_RIGHT;
		};
	}
	
}
