package com.greentree.engine.moon.editor.ui;

import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.signals.ui.Clickable;

import java.util.Objects;

@RequiredComponent({Clickable.class})
public final class Button implements ConstComponent {

    private static final long serialVersionUID = 1L;
    private final String name;

    public Button(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Button other))
            return false;
        return Objects.equals(name, other.name);
    }

    public String name() {
        return name;
    }

}
