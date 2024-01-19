package com.greentree.engine.moon.render;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.options.OptionsProperty;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.DestroyProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.render.window.Window;
import com.greentree.engine.moon.render.window.WindowLibraryProperty;
import com.greentree.engine.moon.render.window.WindowProperty;
import com.greentree.engine.moon.render.window.callback.ButtonAction;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.MousePosition;

public final class WindowModule implements LaunchModule, TerminateModule {

    private final ListenerCloser[] lcs = new ListenerCloser[3];
    private Window window;

    @ReadProperty({DevicesProperty.class, OptionsProperty.class, WindowLibraryProperty.class})
    @CreateProperty({WindowProperty.class})
    @Override
    public void launch(EngineProperties properties) {
        final var manager = properties.get(OptionsProperty.class).provider();
        final var library = properties.get(WindowLibraryProperty.class).library();
        final var title = manager.get("window.title");
        final var width = manager.getInt("window.width");
        final var height = manager.getInt("window.height");
        window = library.createWindow(title, width, height);
        window.makeCurrent();
        final var wwc = new WindowProperty(window);
        properties.add(wwc);
        final var signals = properties.get(DevicesProperty.class).devices();
        lcs[0] = window.addCursorPosCallback((x, y) -> {
            final var fx = 2f * (x - window.getWidth() / 2f) / window.getWidth();
            final var fy = -2f * (y - window.getHeight() / 2f) / window.getHeight();
            MousePosition.X.signal(signals, (float) fx);
            MousePosition.Y.signal(signals, (float) fy);
        });
        lcs[1] = window.addKeyCallback((key, scancode, action, mods) -> {
            switch (action) {
                case PRESS -> key.press(signals);
                case RELEASE -> key.release(signals);
                default -> {
                }
            }
        });
        lcs[2] = window.addMouseButtonCallback((button, action, mods) -> {
            if (action == ButtonAction.PRESS)
                button.press(signals);
            if (action == ButtonAction.RELEASE)
                button.release(signals);
        });
    }

    @ReadProperty({WindowLibraryProperty.class})
    @DestroyProperty({WindowProperty.class})
    @Override
    public void terminate() {
        for (var lc : lcs)
            lc.close();
        window.shouldClose();
    }

}
