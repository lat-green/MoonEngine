package com.greentree.engine.moon.assets.key;

import com.greentree.commons.util.iterator.IteratorUtil;

import java.util.Iterator;

public record IterableAssetKeyImpl(Iterable<AssetKey> keys) implements IterableAssetKey {

    public IterableAssetKeyImpl(AssetKey... keys) {
        this(IteratorUtil.iterable(keys));
    }

    @Override
    public Iterator<AssetKey> iterator() {
        return keys.iterator();
    }

}
