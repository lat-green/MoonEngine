package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.GLFWCallback;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugSeverity;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.render.window.WindowProperty;

import static com.greentree.common.graphics.sgl.enums.gl.GLEnableTarget.*;
import static org.lwjgl.opengl.GL45.*;

public final class OpenGLInitModule implements LaunchModule {

    @WriteProperty({WindowProperty.class})
    @Override
    public void launch(EngineProperties properties) {
        glEnable(TEXTURE_2D.glEnum);
        glEnable(BLEND.glEnum);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(DEBUG_OUTPUT.glEnum);
        glEnable(DEBUG_OUTPUT_SYNCHRONOUS.glEnum);
        glDebugMessageCallback(GLFWCallback.gl((source, type, id, severity, message, userParam) -> {
            if (severity != GLDebugSeverity.DEBUG_SEVERITY_NOTIFICATION) {
                final var severity0 = severity.name;
                var source0 = source.name;
                var type0 = type.name;
                System.err.printf("openGl error (Source: %s Type: %s ID: %d Severity: %s) message:%s\n", source0, type0,
                        id, severity0, message);
            }
        }), 0);
        glClearColor(.6f, .6f, .6f, 1);
        glClearDepth(1.0);
    }

}
