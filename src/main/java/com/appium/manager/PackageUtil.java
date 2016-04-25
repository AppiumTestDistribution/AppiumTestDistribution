package com.appium.manager;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/*
 * This class gets all the classpath for the tests 
 * under the package specified
 */
public class PackageUtil {
	public static Collection<Object> getClasses(final String pack) throws Exception {
		final StandardJavaFileManager fileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(null,
				null, null);
		return StreamSupport
				.stream(fileManager.list(StandardLocation.CLASS_PATH, pack,
						Collections.singleton(JavaFileObject.Kind.CLASS), false).spliterator(), false)
				.map(javaFileObject -> {
					try {
						final String[] split = javaFileObject.getName().replace(".class", "").replace(")", "")
								.split(Pattern.quote(File.separator));

						final String fullClassName = pack + "." + split[split.length - 1];
						return Class.forName(fullClassName);
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}

				}).collect(Collectors.toCollection(ArrayList::new));
	}
}
