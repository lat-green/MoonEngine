package com.greentree.engine.moon.render.shader;

import java.io.Serializable;

public interface ShaderProgramData extends Iterable<ShaderData>, Serializable {

    ShaderData vert();

    ShaderData frag();

    ShaderData geom();

}
