import com.greentree.commons.data.file.DataSerializer;
import com.greentree.engine.moon.base.EntitySerializer;
import com.greentree.engine.moon.base.FileWatcherPollEventsModule;
import com.greentree.engine.moon.base.InitAssetManagerModule;
import com.greentree.engine.moon.base.assets.BaseAssetSerializatorModule;
import com.greentree.engine.moon.base.scene.SceneModule;
import com.greentree.engine.moon.base.time.TimeSystem;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.modules.EngineModule;

open module engine.moon.base {
    requires transitive engine.moon.modules;
    requires transitive engine.moon.ecs;
    requires transitive engine.moon.assets;
    requires transitive commons.math;
    requires transitive commons.xml;
    requires transitive com.google.gson;
    requires transitive org.apache.logging.log4j;
    exports com.greentree.engine.moon.base;
    exports com.greentree.engine.moon.base.assets;
    exports com.greentree.engine.moon.base.assets.xml;
    exports com.greentree.engine.moon.base.assets.json;
    exports com.greentree.engine.moon.base.assets.text;
    exports com.greentree.engine.moon.base.assets.array;
    exports com.greentree.engine.moon.base.assets.number;
    exports com.greentree.engine.moon.base.assets.properties;
    exports com.greentree.engine.moon.base.assets.scene;
    exports com.greentree.engine.moon.base.info;
    exports com.greentree.engine.moon.base.sorter;
    exports com.greentree.engine.moon.base.property.modules;
    exports com.greentree.engine.moon.base.property.modules.info;
    exports com.greentree.engine.moon.base.modules.scanner;
    exports com.greentree.engine.moon.base.component;
    exports com.greentree.engine.moon.base.component.info;
    exports com.greentree.engine.moon.base.time;
    exports com.greentree.engine.moon.base.name;
    exports com.greentree.engine.moon.base.layer;
    exports com.greentree.engine.moon.base.scene;
    exports com.greentree.engine.moon.base.transform;
    provides DataSerializer with EntitySerializer;
    uses ECSSystem;
    uses EngineModule;
    provides ECSSystem with FileWatcherPollEventsModule, TimeSystem;
    provides EngineModule
            with InitAssetManagerModule, SceneModule, BaseAssetSerializatorModule;
}
