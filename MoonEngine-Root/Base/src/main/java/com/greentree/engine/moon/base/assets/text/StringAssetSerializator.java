package com.greentree.engine.moon.base.assets.text;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.InputStreamUtil;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

import java.io.IOException;

public class StringAssetSerializator implements AssetSerializator<String> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(Resource.class, key);
    }

    @Override
    public Asset<String> load(AssetManager manager, AssetKey ckey) {
        if (manager.canLoad(Resource.class, ckey)) {
            final var res = manager.load(Resource.class, ckey);
            return AssetKt.map(res, new StringAsset());
        }
        return null;
    }

    private static final class StringAsset implements Value1Function<Resource, String> {

        private static final long serialVersionUID = 1L;

        @Override
        public String apply(Resource res) {
            if (res == null)
                return null;
            try (final var in = res.open()) {
                return InputStreamUtil.readString(in);
            } catch (IOException e) {
                throw new WrappedException(e);
            }
        }

    }

}
