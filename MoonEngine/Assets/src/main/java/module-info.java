open module engine.moon.assets {
    requires transitive commons.util;
    requires transitive commons.data;
    requires transitive commons.reflection;
    requires transitive engine.moon.kernel;
    exports com.greentree.engine.moon.assets;
    exports com.greentree.engine.moon.assets.asset;
    exports com.greentree.engine.moon.assets.key;
    exports com.greentree.engine.moon.assets.loader;
    exports com.greentree.engine.moon.assets.location;
    exports com.greentree.engine.moon.assets.serializator;
    exports com.greentree.engine.moon.assets.serializator.request;
    exports com.greentree.engine.moon.assets.serializator.loader;
    exports com.greentree.engine.moon.assets.serializator.manager;
    exports com.greentree.engine.moon.assets.serializator.manager.cache;
}