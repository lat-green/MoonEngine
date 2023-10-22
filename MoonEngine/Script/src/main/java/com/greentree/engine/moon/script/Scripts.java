package com.greentree.engine.moon.script;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.ecs.component.ConstComponent;

import java.util.Objects;

public record Scripts(Iterable<? extends Asset<? extends Script>> scripts)
        implements ConstComponent {

    @SafeVarargs
    public Scripts(Asset<? extends Script>... scriptsArray) {
        this(IteratorUtil.iterable(scriptsArray));
    }

    public Scripts {
        Objects.requireNonNull(scripts);
    }

    public Scripts() {
        this(IteratorUtil.empty());
    }

}
