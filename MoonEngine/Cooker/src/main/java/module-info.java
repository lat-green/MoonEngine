open module engine.moon.cooker {
    requires transitive commons.util;
    requires transitive commons.data;
    requires transitive commons.reflection;
    requires transitive engine.moon.assets;
    requires transitive engine.moon.modules;
    requires kotlin.stdlib;
    exports com.greentree.engine.moon.cooker;
    exports com.greentree.engine.moon.cooker.info;
    exports com.greentree.engine.moon.cooker.filter;
}