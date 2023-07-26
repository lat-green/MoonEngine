package com.greentree.engine.moon.base.assets.properties;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

import java.io.IOException;
import java.util.Properties;

public class PropertiesAssetSerializator implements AssetSerializator<Properties> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(Resource.class, key);
    }

    @Override
    public Asset<Properties> load(AssetManager context, AssetKey ckey) {
        {
            final var res = context.load(Resource.class, ckey);
            if (res != null)
                return AssetKt.map(res, PropertiesFunction.INSTANCE);
        }
        return null;
    }

    private static final class PropertiesFunction implements Value1Function<Resource, Properties> {

        private static final long serialVersionUID = 1L;

        private static final PropertiesFunction INSTANCE = new PropertiesFunction();

        @Override
        public Properties apply(Resource res) {
            final var prop = new Properties();
            try (final var in = res.open()) {
                prop.load(in);
            } catch (IOException e) {
                throw new WrappedException(e);
            }
            return prop;
        }

    }

}
