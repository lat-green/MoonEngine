package com.greentree.engine.moon.collision2d.assets;

import com.greentree.commons.geometry.geom2d.IMovableShape2D;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.provider.AssetProvider;
import com.greentree.engine.moon.assets.provider.AssetProviderKt;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;

public class XMLtoShape implements AssetSerializator<IMovableShape2D> {

    @Override
    public AssetProvider<IMovableShape2D> load(AssetLoader.Context manager, AssetKey key) {
        var v = manager.load(XMLElement.class, key);
        return AssetProviderKt.map(v, new XMLtoShapeFunction());
    }

    private static final class XMLtoShapeFunction implements Value1Function<XMLElement, IMovableShape2D> {

        @Override
        public IMovableShape2D apply(XMLElement value) {
            var type = value.getAttribute("type").toLowerCase();
            switch (type) {
                case "circle" -> {
                    var radius = Float.parseFloat(value.getChildren("radius").getContent());
                    return new Circle2D(radius);
                }
                case "capsule" -> {
                }
            }
            throw new IllegalArgumentException("unsupported type " + type);
        }

    }

}
