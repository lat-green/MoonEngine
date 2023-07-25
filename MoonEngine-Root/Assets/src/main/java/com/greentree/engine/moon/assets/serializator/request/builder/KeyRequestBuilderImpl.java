package com.greentree.engine.moon.assets.serializator.request.builder;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequest;
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequestImpl;

import java.util.function.Function;

public final class KeyRequestBuilderImpl<T, R> implements KeyRequestBuilder<T, R> {

    private final Function<? super KeyLoadRequest<T>, ? extends R> manager;
    private final TypeInfo<T> type;
    private AssetKey key;

    public KeyRequestBuilderImpl(Function<? super KeyLoadRequest<T>, ? extends R> manager,
                                 TypeInfo<T> type) {
        this.manager = manager;
        this.type = type;
    }

    @Override
    public R load() {
        return manager.apply(new KeyLoadRequestImpl<T>(type, key));
    }

    @Override
    public KeyRequestBuilder<T, R> set(AssetKey key) {
        this.key = key;
        return this;
    }

}
