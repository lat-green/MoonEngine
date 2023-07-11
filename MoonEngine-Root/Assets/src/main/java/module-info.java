open module engine.moon.assets {
    requires transitive commons.data;
    requires transitive commons.reflection;
    requires transitive engine.moon.kernel;
    exports com.greentree.engine.moon.assets;
    exports com.greentree.engine.moon.assets.key;
    exports com.greentree.engine.moon.assets.location;
    exports com.greentree.engine.moon.assets.serializator;
    exports com.greentree.engine.moon.assets.serializator.context;
    exports com.greentree.engine.moon.assets.serializator.manager;
    exports com.greentree.engine.moon.assets.serializator.request;
    exports com.greentree.engine.moon.assets.serializator.request.builder;
    exports com.greentree.engine.moon.assets.value;
    exports com.greentree.engine.moon.assets.value.merge;
    exports com.greentree.engine.moon.assets.value.provider;
    exports com.greentree.engine.moon.assets.value.function;
    exports com.greentree.engine.moon.assets.serializator.manager.cache;
}
