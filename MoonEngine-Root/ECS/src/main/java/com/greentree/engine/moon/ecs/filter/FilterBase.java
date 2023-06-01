package com.greentree.engine.moon.ecs.filter;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.component.Component;

public interface FilterBase<E extends Entity> extends Iterable<E>, AutoCloseable {

    @Override
    void close();

    default <T extends Component> Iterable<? extends T> get(Class<T> cls) {
        return IteratorUtil.map(this, e -> e.get(cls));
    }

}
