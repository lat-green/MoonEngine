package com.greentree.engine.moon.demo1.cook;

import com.greentree.commons.xml.SAXXMLParser;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.cooker.AssetCookerModule;
import com.greentree.engine.moon.cooker.AssetImportManagerProperty;
import com.greentree.engine.moon.cooker.filter.DependencyTypeAssetImportFilter;
import com.greentree.engine.moon.cooker.filter.PrimaryTypeAssertImportFilter;
import com.greentree.engine.moon.modules.property.EngineProperties;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class TestAssetCookerModule implements AssetCookerModule {

    private final Map<String, Long> count = new HashMap<>();

    @Override
    public void launch(EngineProperties properties) {
        var manager = properties.get(AssetImportManagerProperty.class).manager();
        count.clear();
        manager.addFilter(new DependencyTypeAssetImportFilter(asset -> {
            if ("glsl".equals(asset.getFileType()))
                return true;
            if ("skybox".equals(asset.getFileType()))
                return true;
            return "material".equals(asset.getFileType());
        }, asset -> {
            try (final var in = asset.open()) {
                var ps = new Properties();
                ps.load(in);
                return ps.values().stream().map(x -> x.toString()).toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        manager.addFilter(new DependencyTypeAssetImportFilter(asset -> "xml".equals(asset.getFileType()), asset -> {
            try (final var in = asset.open()) {
                var xml = SAXXMLParser.parse(in);
                return getDependencies(xml);
            } catch (IOException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }));
        manager.addFilter(new PrimaryTypeAssertImportFilter("glsl"));
        manager.addFilter(new PrimaryTypeAssertImportFilter("xml"));
        manager.addFilter(new PrimaryTypeAssertImportFilter("ini"));
    }

    private Collection<String> getDependencies(XMLElement xml) {
        var ch = xml.getChildrens();
        if (ch.isEmpty()) {
            var content = xml.getContent();
            if (content.isBlank())
                return Set.of();
            if (isNumber(content))
                return Set.of();
            if (content.contains("{") || content.contains("}") || content.contains("$"))
                return Set.of();
            return Set.of(content);
        }
        return ch.stream().flatMap(x -> getDependencies(x).stream()).distinct().toList();
    }

    private boolean isNumber(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
