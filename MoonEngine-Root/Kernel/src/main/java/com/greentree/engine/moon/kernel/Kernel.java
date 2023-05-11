package com.greentree.engine.moon.kernel;

import java.util.ArrayList;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Kernel {
	
	public static ConfigurableApplicationContext launch(String[] args) {
		var application = new SpringApplication(primarySources());
		application.setBannerMode(Mode.OFF);
		application.setLogStartupInfo(false);
		return application.run(args);
	}

	
	private static Class<?>[] primarySources() {
		var result = new ArrayList<Class<?>>();
		var stackTrace = Thread.currentThread().getStackTrace();
		for(int i = 2; i < stackTrace.length; i++) {
			var name = stackTrace[i].getClassName();
			try {
				result.add(Class.forName(name));
			}catch(ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return result.toArray(new Class[result.size()]);
	}
}
