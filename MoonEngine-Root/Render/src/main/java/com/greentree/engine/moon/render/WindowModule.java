package com.greentree.engine.moon.render;

import java.util.Properties;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.modules.CreateProperty;
import com.greentree.engine.moon.modules.DestroyProperty;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.ReadProperty;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.render.window.Window;
import com.greentree.engine.moon.render.window.WindowLibraryProperty;
import com.greentree.engine.moon.render.window.WindowProperty;
import com.greentree.engine.moon.render.window.callback.ButtonAction;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.keyboard.KeyBoardButton;
import com.greentree.engine.moon.signals.mouse.MouseButtonDevice;
import com.greentree.engine.moon.signals.mouse.MouseXDevice;
import com.greentree.engine.moon.signals.mouse.MouseYDevice;

public final class WindowModule implements LaunchModule, TerminateModule {
	
	private Window window;
	private final ListenerCloser[] lcs = new ListenerCloser[3];
	
	@ReadProperty({DevicesProperty.class, AssetManagerProperty.class, WindowLibraryProperty.class})
	@CreateProperty({WindowProperty.class})
	@Override
	public void launch(EngineProperties properties) {
		final var manager = properties.get(AssetManagerProperty.class).manager();
		final var library = properties.get(WindowLibraryProperty.class).library();
		
		final var wini = manager.loadData(Properties.class, "window.ini");
		final var title = wini.getProperty("window.title");
		final var width = Integer.parseInt(wini.getProperty("window.width"));
		final var height = Integer.parseInt(wini.getProperty("window.height"));
		
		window = library.createWindow(title, width, height);
		window.makeCurrent();
		final var wwc = new WindowProperty(window);
		properties.add(wwc);
		
		final var signals = properties.get(DevicesProperty.class).devices();
		
		lcs[0] = window.addCursorPosCallback((x, y) -> {
			final var fx = 2f * (x - window.getWidth() / 2f) / window.getWidth();
			final var fy = -2f * (y - window.getHeight() / 2f) / window.getHeight();
			new MouseXDevice().signal(signals, (float) fx);
			new MouseYDevice().signal(signals, (float) fy);
		});
		lcs[1] = window.addKeyCallback((key, scancode, action, mods) -> {
			var d = new KeyBoardButton(key);
			switch(action) {
				case PRESS -> d.press(signals);
				case RELEASE -> d.release(signals);
				default -> {
				}
			}
		});
		lcs[2] = window.addMouseButtonCallback((button, action, mods) -> {
			if(action == ButtonAction.PRESS)
				new MouseButtonDevice(button).press(signals);
			if(action == ButtonAction.RELEASE)
				new MouseButtonDevice(button).release(signals);
		});
	}
	
	@DestroyProperty({WindowProperty.class})
	@Override
	public void terminate() {
		for(var lc : lcs)
			lc.close();
		window.shouldClose();
	}
	
}
