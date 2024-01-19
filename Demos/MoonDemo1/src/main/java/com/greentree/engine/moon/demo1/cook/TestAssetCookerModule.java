package com.greentree.engine.moon.demo1.cook;

import com.greentree.commons.xml.SAXXMLParser;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.cooker.AssetCookerModule;
import com.greentree.engine.moon.cooker.AssetImportManagerProperty;
import com.greentree.engine.moon.cooker.filter.DependencyTypeAssetImportFilter;
import com.greentree.engine.moon.cooker.filter.PrimaryFilterAssetImportFilter;
import com.greentree.engine.moon.cooker.filter.PrimaryTypeAssetImportFilter;
import com.greentree.engine.moon.modules.property.EngineProperties;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.stream.Stream;

public class TestAssetCookerModule implements AssetCookerModule {

    @Override
    public void launch(EngineProperties properties) {
        var manager = properties.get(AssetImportManagerProperty.class).manager();
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
        manager.addFilter(new DependencyTypeAssetImportFilter(asset -> "xml".equals(asset.getFileType()) || "scene".equals(asset.getFileType()), asset -> {
            try (final var in = asset.open()) {
                var xml = SAXXMLParser.parse(in);
                return getDependencies(xml);
            } catch (IOException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }));
        manager.addFilter(new PrimaryTypeAssetImportFilter("properties"));
        manager.addFilter(new PrimaryFilterAssetImportFilter(asset -> "scene/world1.scene".equals(asset.getFileName())));
        manager.addImporter(new EntityRefIncludeAssetImporter());
    }

    private Collection<String> getDependencies(XMLElement xml) {
        var ch = xml.getChildrens();
        if (ch.isEmpty()) {
            var result = new HashSet<String>();
            result.add(xml.getContent());
            result.removeIf(content -> content.isBlank() || content.contains("{") || content.contains("}") || content.contains("$") || isNumber(content));
            return result;
        }
        var result = ch.stream().flatMap(x -> getDependencies(x).stream());
        if ("entity_ref".equals(xml.getName())) {
            return Stream.concat(result, xml.getAttributes().values().stream()).distinct().toList();
        }
        return result.distinct().toList();
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
