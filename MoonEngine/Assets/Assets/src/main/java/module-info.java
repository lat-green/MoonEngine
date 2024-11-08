open module engine.moon.assets {
    requires transitive commons.util;
    requires transitive commons.data;
    requires transitive commons.reflection;
    requires transitive engine.moon.kernel;
    exports com.greentree.engine.moon.assets.asset;
    exports com.greentree.engine.moon.assets.cache;
    exports com.greentree.engine.moon.assets.exception;
    exports com.greentree.engine.moon.assets.key;
    exports com.greentree.engine.moon.assets.loader;
    exports com.greentree.engine.moon.assets.manager;
    exports com.greentree.engine.moon.assets.serializator;
}