package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.base.EngineBase;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        EngineBase.launch(args, new InitAssetModule(), new InitSceneModule(), new ExitOnESCAPE());
    }

}
