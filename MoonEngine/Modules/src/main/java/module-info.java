open module engine.moon.modules {
    requires kotlin.stdlib;
    requires transitive commons.graph;
    requires transitive engine.moon.kernel;
    exports com.greentree.engine.moon.modules;
    exports com.greentree.engine.moon.modules.property;
}
