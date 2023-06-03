import com.greentree.engine.moon.demo1.InitControllerSystem;
import com.greentree.engine.moon.ecs.system.ECSSystem;

open module game.moon.demo1 {
    requires transitive engine.moon.opengl;
    requires transitive engine.moon.script;
    requires transitive engine.moon.assimp;
    provides ECSSystem with InitControllerSystem;
}
