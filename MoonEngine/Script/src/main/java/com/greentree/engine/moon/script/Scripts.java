package com.greentree.engine.moon.script;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.Asset;
import com.greentree.engine.moon.ecs.component.ConstComponent;

import java.io.Serial;
import java.util.Objects;

public final class Scripts implements ConstComponent {

    @Serial
    private static final long serialVersionUID = 0L;

    private final Iterable<? extends Asset<? extends Script>> scripts;

    @SafeVarargs
    public Scripts(Asset<? extends Script>... scriptsArray) {
        this(IteratorUtil.iterable(scriptsArray));
    }

    public Scripts(Iterable<? extends Asset<? extends Script>> scripts) {
        Objects.requireNonNull(scripts);
        this.scripts = scripts;
    }

    public Scripts() {
        this(IteratorUtil.empty());
    }

    public Iterable<? extends Asset<? extends Script>> scripts() {
        return scripts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scripts);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Scripts) obj;
        return Objects.equals(this.scripts, that.scripts);
    }

    @Override
    public String toString() {
        return "Scripts[" + scripts + ']';
    }

}
