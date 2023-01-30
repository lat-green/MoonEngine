package com.greentree.engine.moon.opengl;

import static com.greentree.common.graphics.sgl.enums.gl.GLEnableTarget.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.*;

import java.util.Properties;

import com.greentree.common.graphics.sgl.GLFWCallback;
import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.common.graphics.sgl.SGLFW.ButtonAction;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugSeverity;
import com.greentree.common.graphics.sgl.window.Window;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.TerminateModule;
import com.greentree.engine.moon.module.annotation.CreateProperty;
import com.greentree.engine.moon.module.annotation.DestroyProperty;
import com.greentree.engine.moon.module.annotation.ReadProperty;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.keyboard.KeyBoardButton;
import com.greentree.engine.moon.signals.mouse.MouseButtonDevice;
import com.greentree.engine.moon.signals.mouse.MouseXDevice;
import com.greentree.engine.moon.signals.mouse.MouseYDevice;

public final class WindowModule implements LaunchModule, TerminateModule {
	
	private Window window;
	private final ListenerCloser[] lcs = new ListenerCloser[4];
	
	@ReadProperty({DevicesProperty.class,AssetManagerProperty.class,SceneManagerProperty.class})
	@CreateProperty({WindowProperty.class})
	@Override
	public void launch(EngineProperties properties) {
		final var scsnes = properties.get(SceneManagerProperty.class).manager();
		
		scsnes.addGlobalSystem(new GLFWUpdateEvents());
		scsnes.addGlobalSystem(new ExitOnWindowShouldClose());
		
		final var manager = properties.get(AssetManagerProperty.class).manager();
		
		final var wini = manager.loadData(Properties.class, "window.ini");
		final var title = wini.getProperty("window.title");
		final var width = Integer.parseInt(wini.getProperty("window.width"));
		final var height = Integer.parseInt(wini.getProperty("window.height"));
		
		SGLFW.init();
		window = new Window(title, width, height);
		final var wwc = new WindowProperty(window);
		properties.add(wwc);
		window.makeCurrent();
		
		glEnable(TEXTURE_2D.glEnum);
		glEnable(BLEND.glEnum);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(DEBUG_OUTPUT.glEnum);
		glEnable(DEBUG_OUTPUT_SYNCHRONOUS.glEnum);
		glDebugMessageCallback(GLFWCallback.gl((source, type, id, severity, message, userParam)-> {
			if(severity != GLDebugSeverity.DEBUG_SEVERITY_NOTIFICATION) {
				final var severity0 = severity.name;
				var source0 = source.name;
				var type0 = type.name;
				System.err.printf(
						"openGl error (Source: %s Type: %s ID: %d Severity: %s) message:%s\n",
						source0, type0, id, severity0, message);
			}
		}), 0);
		
		glClearColor(.6f, .6f, .6f, 1);
		glClearDepth(1.0);
		
		final var signals = properties.get(DevicesProperty.class).devices();
		
		lcs[0] = window.getMousePosition().addListener((x, y)-> {
			final var fx = 2f * x / window.getWidth();
			final var fy = 2f * y / window.getHeight();
			new MouseXDevice().signal(signals, fx);
			new MouseYDevice().signal(signals, fy);
		});
		lcs[1] = window.getKeyPress().addListener(key-> {
			new KeyBoardButton(key.nonGL()).press(signals);
		});
		lcs[2] = window.getKeyRelease().addListener(key-> {
			new KeyBoardButton(key.nonGL()).release(signals);
		});
		lcs[3] = window.addMouseButtonCallback((button, action, mods)-> {
			if(action == ButtonAction.PRESS)
				new MouseButtonDevice(button.nonGL()).press(signals);
			if(action == ButtonAction.RELEASE)
				new MouseButtonDevice(button.nonGL()).release(signals);
		});
	}
	
	@DestroyProperty({WindowProperty.class})
	@Override
	public void terminate() {
		for(var lc : lcs)
			lc.close();
		Window.unmakeCurrent();
		window.shouldClose();
		SGLFW.terminate();
	}
	
}
