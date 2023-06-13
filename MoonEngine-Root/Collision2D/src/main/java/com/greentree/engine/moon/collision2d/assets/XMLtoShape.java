package com.greentree.engine.moon.collision2d.assets;

import com.greentree.commons.geometry.geom2d.IMovableShape2D;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

public class XMLtoShape implements AssetSerializator<IMovableShape2D> {

    @Override
    public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
        return manager.canLoad(XMLElement.class, key);
    }

    @Override
    public Value<IMovableShape2D> load(LoadContext context, AssetKey key) {
        if (context.canLoad(XMLElement.class, key)) {
            var v = context.load(XMLElement.class, key);
            return context.map(v, new XMLtoShapeFunction());
        }
        return null;
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
