package com.greentree.engine.moon.bean;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.stream.Stream;

import com.greentree.engine.moon.Engine;
import com.greentree.engine.moon.bean.annotation.Autowired;
import com.greentree.engine.moon.bean.annotation.BeanScan;
import com.greentree.engine.moon.bean.annotation.EngineBean;
import com.greentree.engine.moon.module.base.AnnotationUtil;

@EngineBean
public class PackageScanEngineBeanProcessor implements ClassAnnotationEngineBeanProcessor<BeanScan> {
	
	@Autowired(required = true)
	private EngineContext context;
	
	public static Stream<String> getAllClassNames(String pkg) {
		var result = new ArrayList<String>();
		Enumeration<URL> en;
		try {
			en = Engine.class.getClassLoader().getResources("META-INF");
			while(en.hasMoreElements()) {
				var url = en.nextElement();
				var con = url.openConnection();
				if(con instanceof JarURLConnection urlcon)
					try(var jar = urlcon.getJarFile();) {
						var entries = jar.entries();
						while(entries.hasMoreElements()) {
							var entry = entries.nextElement().getName();
							result.add(entry);
						}
					}
				else {
					var file = new File(url.toURI()).getParentFile();
					var length = file.toString().length() + 1;
					
					var path = Files.walk(file.toPath()).map(Path::toFile).filter(File::isFile)
							.map(File::toString).map(x -> x.substring(length)).toList();
					result.addAll(path);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.stream().filter(x -> !x.contains("module-info")).filter(x -> !x.startsWith("META-INF"))
				.filter(x -> x.endsWith(".class")).map(x -> x.substring(0, x.length() - 6))
				.map(x -> x.replace('/', '.')).map(x -> x.replace('\\', '.')).filter(x -> x.startsWith(pkg));
	}
	
	@Override
	public void processAnnotation(Object bean, BeanScan annotation) {
		getAllClassNames(bean.getClass().getPackageName()).map(x -> {
			try {
				return Class.forName(x);
			}catch(ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}).filter(x -> !x.isAnnotation()).filter(x -> AnnotationUtil.hasAnnotation(x, EngineBean.class)).forEach(x -> {
			context.addBean(x);
		});
	}
	
}