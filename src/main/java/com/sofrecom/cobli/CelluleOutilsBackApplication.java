package com.sofrecom.cobli;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableScheduling
public class CelluleOutilsBackApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CelluleOutilsBackApplication.class, args);
		System.out.println("Start");
		
		/*
		final String className = "javademo";
		Path temp = Paths.get(System.getProperty("java.io.tmpdir"), className);
		Files.createDirectories(temp);
		
		Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), className + ".java");
		System.out.println("The java source file is loacted at "+javaSourceFile);
		
		final String toolsJarFileName = "tools.jar";
		final String javaHome = System.getProperty("java.home");
		Path toolsJarFilePath = Paths.get(javaHome, "lib", toolsJarFileName);
		if (!Files.exists(toolsJarFilePath)){
			System.out.println("The tools jar file ("+toolsJarFileName+") could not be found at ("+toolsJarFilePath+").");
		}
		
		// Definition of the files to compile
		File[] files1 = {javaSourceFile.toFile()};
		// Get the compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// Get the file system manager of the compiler
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		// Create a compilation unit (files)
		Iterable<? extends JavaFileObject> compilationUnits =
				fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files1));
		// A feedback object (diagnostic) to get errors
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		// Compilation unit can be created and called only once
		JavaCompiler.CompilationTask task = compiler.getTask(
				null,
				fileManager,
				diagnostics,
				null,
				null,
				compilationUnits
		);
		// The compile task is called
		task.call();
		// Printing of any compile problems
		for (Diagnostic diagnostic : diagnostics.getDiagnostics())
			System.out.format("Error on line %d in %s%n",
					diagnostic.getLineNumber(),
					diagnostic.getSource());

		// Close the compile resources
		fileManager.close();
		
		ClassLoader classLoader = CelluleOutilsBackApplication.class.getClassLoader();
		URLClassLoader urlClassLoader = new URLClassLoader(
				new URL[] { temp.toUri().toURL() },
				classLoader);
		Class javaDemoClass = urlClassLoader.loadClass(className);
		Method method = javaDemoClass.getMethod("run");
		method.invoke(null); // null because this is a static method
				
		*/		
	}
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}
