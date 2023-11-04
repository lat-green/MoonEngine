package com.greentree.engine.moon.base;

import com.greentree.engine.moon.base.info.AllReadSceneCWRDMethodInfo;
import com.greentree.engine.moon.base.info.AnnotatedCWRDMethodPropertyInfo;
import com.greentree.engine.moon.base.info.MergeCWRDMethodInfo;
import com.greentree.engine.moon.base.modules.scanner.CollectionModuleDefenitionScanner;
import com.greentree.engine.moon.base.modules.scanner.ConfigModuleContainerScanner;
import com.greentree.engine.moon.base.modules.scanner.ModuleDefenition;
import com.greentree.engine.moon.base.modules.scanner.ServiceLoaderModuleDefenitionScanner;
import com.greentree.engine.moon.base.sorter.MethodSorter;
import com.greentree.engine.moon.base.sorter.OnCWRDMethodSorter;
import com.greentree.engine.moon.modules.*;
import com.greentree.engine.moon.modules.property.EngineProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EngineBase {

    public static final MethodSorter SORTER;
    private static final Logger LOG = LogManager.getLogger(EngineBase.class);

    static {
        var info = new AnnotatedCWRDMethodPropertyInfo();
        SORTER = new OnCWRDMethodSorter(new MergeCWRDMethodInfo(info, new AllReadSceneCWRDMethodInfo(info)));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static EngineProperties launch(String[] args, EngineModule... modules) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        var scanner = new ConfigModuleContainerScanner()
                .addScanner(new ServiceLoaderModuleDefenitionScanner())
                .addScanner(new CollectionModuleDefenitionScanner(modules));
        var scanModules = scanner.scan().map(ModuleDefenition::build).toList();
        var launchModules = new ArrayList<LaunchModule>(
                (List) scanModules.stream().filter(x -> x instanceof LaunchModule).toList());
        var updateModules = new ArrayList<UpdateModule>(
                (List) scanModules.stream().filter(x -> x instanceof UpdateModule).toList());
        var terminateModules = new ArrayList<TerminateModule>(
                (List) scanModules.stream().filter(x -> x instanceof TerminateModule).toList());
        SORTER.sort(launchModules, "launch");
        SORTER.sort(updateModules, "update");
        SORTER.sort(terminateModules, "terminate");
        for (var m : launchModules)
            LOG.info("with launch module " + m);
        for (var m : updateModules)
            LOG.info("with update module " + m);
        for (var m : terminateModules)
            LOG.info("with terminate module " + m);
        return Engine.launch(args, p -> {
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
