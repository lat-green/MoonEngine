open module engine.moon.ecs {
    requires transitive commons.action;
    requires transitive commons.graph;
    requires transitive engine.moon.modules;
    requires transitive kotlin.stdlib;
    exports com.greentree.engine.moon.ecs;
    exports com.greentree.engine.moon.ecs.scene;
    exports com.greentree.engine.moon.ecs.pool;
    exports com.greentree.engine.moon.ecs.filter;
    exports com.greentree.engine.moon.ecs.filter.builder;
    exports com.greentree.engine.moon.ecs.filter.builder.world;
    exports com.greentree.engine.moon.ecs.system;
    exports com.greentree.engine.moon.ecs.system.debug;
    exports com.greentree.engine.moon.ecs.component;
}
