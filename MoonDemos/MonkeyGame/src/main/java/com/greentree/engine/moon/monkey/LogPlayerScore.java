package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.name.Names;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class LogPlayerScore implements WorldInitSystem, UpdateSystem {

    private WorldEntity player1, player2;
    private int score1, score2;

    @Override
    public void update() {
        var s1 = player1.get(Player.class).score;
        var s2 = player2.get(Player.class).score;
        if (s1 != score1 || s2 != score2) {
            score1 = s1;
            score2 = s2;
            System.out.println("score 1:" + score1);
            System.out.println("score 2:" + score2);
            System.out.println();

        }
    }

    @Override
    public void init(World world, SceneProperties properties) {
        score1 = -1;
        score2 = -1;
        player1 = properties.get(Names.class).get("player1");
        player2 = properties.get(Names.class).get("player2");
    }

}
