package com.greentree.engine.moon.base;

import com.greentree.engine.moon.base.modules.scanner.CollectionModuleDefenitionScanner;
import com.greentree.engine.moon.base.modules.scanner.ConfigModuleContainerScanner;
import com.greentree.engine.moon.base.modules.scanner.ModuleDefenition;
import com.greentree.engine.moon.base.modules.scanner.ServiceLoaderModuleDefenitionScanner;
import com.greentree.engine.moon.base.property.modules.info.AnnotatedCWRDMethodPropertyInfo;
import com.greentree.engine.moon.base.sorter.OnCWRDMethodSorter;
import com.greentree.engine.moon.modules.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EngineBase {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void launch(String[] args, EngineModule... modules) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        var scanner = new ConfigModuleContainerScanner()
                .addScanner(new ServiceLoaderModuleDefenitionScanner())
                .addScanner(new CollectionModuleDefenitionScanner(modules));
        var scanModules = scanner.scan().map(ModuleDefenition::build).toList();
        var sorter = new OnCWRDMethodSorter(new AnnotatedCWRDMethodPropertyInfo());
        var launchModules = new ArrayList<LaunchModule>(
                (List) scanModules.stream().filter(x -> x instanceof LaunchModule).toList());
        var updateModules = new ArrayList<UpdateModule>(
                (List) scanModules.stream().filter(x -> x instanceof UpdateModule).toList());
        var terminateModules = new ArrayList<TerminateModule>(
                (List) scanModules.stream().filter(x -> x instanceof TerminateModule).toList());
        sorter.sort(launchModules, "launch");
        sorter.sort(updateModules, "update");
        sorter.sort(terminateModules, "terminate");
        Engine.launch(args, p -> {
            for (var module : launchModules)
                module.launch(p);
        }, () -> {
            for (var module : updateModules)
                module.update();
        }, () -> {
            for (var module : terminateModules)
                module.terminate();
        });
    }

}
