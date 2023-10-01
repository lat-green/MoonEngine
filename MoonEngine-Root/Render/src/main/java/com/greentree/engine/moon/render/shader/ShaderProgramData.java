package com.greentree.engine.moon.render.shader;

import com.greentree.commons.util.iterator.IteratorUtil;

import java.io.Serializable;
import java.util.Iterator;

public interface ShaderProgramData extends Iterable<ShaderData>, Serializable {

    @Override
    default Iterator<ShaderData> iterator() {
        var geom = geom();
        if (geom != null)
            return IteratorUtil.iterator(vert(), frag(), geom);
        return IteratorUtil.iterator(vert(), frag());
    }

    ShaderData geom();

    ShaderData vert();

    ShaderData frag();

}
