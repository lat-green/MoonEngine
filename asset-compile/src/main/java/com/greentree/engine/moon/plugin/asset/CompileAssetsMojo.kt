package com.greentree.engine.moon.plugin.asset

import com.greentree.engine.moon.cooker.AssetCookerModule
import com.greentree.engine.moon.cooker.AssetImportManagerProperty
import com.greentree.engine.moon.cooker.AssetImporter
import com.greentree.engine.moon.cooker.FolderAssetImportManager
import com.greentree.engine.moon.modules.property.EnginePropertiesBase
import org.apache.maven.execution.MavenSession
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.BuildPluginManager
import org.apache.maven.plugins.annotations.Component
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.plugins.annotations.ResolutionScope
import org.apache.maven.project.MavenProject
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.lang.reflect.Modifier
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile

@Mojo(
	name = "cook",
	defaultPhase = LifecyclePhase.PROCESS_CLASSES,
	requiresDependencyResolution = ResolutionScope.COMPILE
)
class CompileAssetsMojo : AbstractMojo() {

	@Component
	var mavenProject: MavenProject? = null

	@Component
	var mavenSession: MavenSession? = null

	@Component
	var pluginManager: BuildPluginManager? = null

	@Parameter(property = "assetDirectory", defaultValue = "assets")
	var assetDirectory: File? = null

	override fun execute() {
		val mavenProject = mavenProject!!
		try {
			val assetDirectory = assetDirectory

			if(assetDirectory == null || !assetDirectory.exists()) {
				log.warn("skip $assetDirectory is not exists")
				return
			}
			val allClasses = getAllClasses(mavenProject)
			val assetCookerModules =
				allClasses
					.filter {
						AssetCookerModule::class.java.isAssignableFrom(it)
					}.filter {
						!Modifier.isAbstract(it.modifiers)
					}.map { it.getConstructor().newInstance() as AssetCookerModule }
			val properties = EnginePropertiesBase()
			val importManager = FolderAssetImportManager(assetDirectory, File(mavenProject.build.outputDirectory))
			properties.add(AssetImportManagerProperty(importManager))
			for(m in assetCookerModules)
				m.launch(properties)
			val ctx = object : AssetImporter.Context {
				override fun ask(text: String): Boolean {
					TODO("Not yet implemented")
				}

				override fun <T> findClass(baseClass: Class<T>, name: String) = allClasses.filter {
					baseClass.isAssignableFrom(it) && it.name.endsWith(name)
				}.minByOrNull { it.name.length } as Class<out T>?
					?: throw ClassNotFoundException("baseClass = $baseClass, name = $name")

				override fun readFile(name: String) = BufferedInputStream(FileInputStream(File(assetDirectory, name)))
			}
			for(file in getAllFiles(assetDirectory)) {
				try {
					when(importManager.importAsset(ctx, file)) {
						null -> log.debug("ignore $file")
						else -> log.debug("import $file")
					}
				} catch(e: Exception) {
					log.warn("can't import $file", e)
				}
			}
			importManager.build()
			for(m in assetCookerModules)
				m.terminate()
		} catch(e: Throwable) {
			e.printStackTrace()
		}
	}

	private fun getClassLoader(project: MavenProject): ClassLoader {
		val classPathUrls = mutableListOf<URL>()
		val artifacts = project.dependencyArtifacts
		for(artifact in artifacts) {
			artifact?.file?.toURI()?.toURL()?.let {
				classPathUrls.add(it)
			}
		}
		val classpathElements = project.compileClasspathElements
		classpathElements.add(project.build.outputDirectory)
		classpathElements.add(project.build.testOutputDirectory)
		for(classpathElement in classpathElements) {
			classPathUrls.add(File(classpathElement).toURI().toURL())
		}
		return URLClassLoader(classPathUrls.toTypedArray(), this.javaClass.classLoader)
	}

	private fun getAllClasses(project: MavenProject) = run {
		val loader = getClassLoader(project)
		project.compileClasspathElements.map { File(it) }.flatMap { file ->
			if(file.isDirectory)
				getAllFiles(file).mapNotNull {
					kotlin.runCatching { it.absolutePath.substring(file.absolutePath.length + 1) }.getOrNull()
				}
			else {
				JarFile(file).use { file ->
					file.entries().toList().map { it.name }
				}
			}
		}.filter { it.endsWith(".class") && "-" !in it }
			.map { it.substring(0, it.length - 6).replace('/', '.').replace('\\', '.') }
			.mapNotThrow { loader.loadClass(it)!! }
	}

	private fun getAllFiles(file: File): List<File> =
		if(file.isDirectory)
			file.listFiles().flatMap { getAllFiles(it.canonicalFile) }
		else
			listOf(file)
}

private inline fun <E, R> Iterable<E>.mapNotThrow(block: (E) -> R) =
	mapNotNull { kotlin.runCatching { block(it) }.getOrNull() }

private fun openExplorer(file: File): Int {
	val builder = ProcessBuilder("cmd.exe", "/c", "explorer $file")
	builder.redirectErrorStream(true)
	val process = builder.start()
	return process.exitValue()
}