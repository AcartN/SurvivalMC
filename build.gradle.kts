plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

group = "fr.jeanjacquelin.survival"
version = "1.0-SNAPSHOT"

val main = "${project.group}.MainKt"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.ExperimentalStdlibApi")
        optIn.add("kotlinx.serialization.ExperimentalSerializationApi")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.kyori)
    compileOnly(libs.paper.api)

    // Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
}

val mainSourceSet: SourceSet = sourceSets["main"]

tasks {
    val mainClassName = "$group.MainKt"
    createJarTask("plugin", mainClassName, "plugin", true)
}

/**
 * Create a task that generates an uber jar with:
 * - The application itself
 * - All the dependencies of the application
 * - A manifest with Main-Class set (makes the jar runnable)
 *
 * [appendix] is used to name the task and the jar
 */
fun TaskContainer.createJarTask(
    name: String? = null,
    mainClassName: String? = null,
    appendix: String? = null,
    includeLibraries: Boolean? = null,
): Jar {
    if (includeLibraries == null) {
        createJarTask(name, mainClassName, appendix, true)
        return createJarTask(name, mainClassName, appendix, false)
    }
    val jarTaskName = name?.let { it + if (includeLibraries) "UberJar" else "Jar" } ?: when {
        appendix == null -> if (includeLibraries) "uberJar" else "jar"
        includeLibraries -> "${appendix}UberJar"
        else -> "${appendix}Jar"
    }

    return create<Jar>(jarTaskName) {
        group = "Jar"
        archiveAppendix.set(appendix)

        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        // Add the application
        from(mainSourceSet.output)

        if (includeLibraries) {
            archiveClassifier.set("uber")
            // Add all libraries
            exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
            dependsOn(mainSourceSet.runtimeClasspath)
            from({
                mainSourceSet.runtimeClasspath.filter { it.name.endsWith("jar") }
                    .map { zipTree(it) }
            })
        }

        // Optional BUT needed if you want to make the jar runnable
        // It specifies the location of the main class to run
        mainClassName?.let {
            manifest {
                attributes(
                    mapOf(
                        "Main-Class" to mainClassName,
                        "paperweight-mappings-namespace" to "mojang",
                    )
                )
            }
        }
    }
}
