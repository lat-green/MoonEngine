package com.greentree.engine.moon.base.assets.xml;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.xml.SAXXMLParser;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

import java.io.ByteArrayInputStream;

public class XMLAssetSerializator implements AssetSerializator<XMLElement> {

    @Override
    public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
        return manager.canLoad(Resource.class, key) || manager.canLoad(String.class, key);
    }

    @Override
    public Value<XMLElement> load(LoadContext context, AssetKey key) {
        if (context.canLoad(Resource.class, key)) {
            final var res = context.load(Resource.class, key);
            return context.map(res, XMLFunction.INSTANCE);
        }
        if (context.canLoad(String.class, key)) {
            final var text = context.load(String.class, key);
            return context.map(text, XMLTextFunction.INSTANCE);
        }
        return null;
    }

    private static final class XMLFunction implements Value1Function<Resource, XMLElement> {

        public static final XMLFunction INSTANCE = new XMLFunction();
        private static final long serialVersionUID = 1L;

        @SuppressWarnings("deprecation")
        @Override
        public XMLElement apply(Resource res) {
            try (final var in = res.open()) {
                return SAXXMLParser.parse(in);
            } catch (Exception e) {
                throw new IllegalArgumentException(res.toString(), e);
            }
        }

    }

    private static final class XMLTextFunction implements Value1Function<String, XMLElement> {

        public static final XMLTextFunction INSTANCE = new XMLTextFunction();
        private static final long serialVersionUID = 1L;

        @SuppressWarnings("deprecation")
        @Override
        public XMLElement apply(String text) {
            try (final var bout = new ByteArrayInputStream(text.getBytes())) {
                return SAXXMLParser.parse(bout);
            } catch (Exception e) {
                throw new IllegalArgumentException(text, e);
            }
        }

    }

}
