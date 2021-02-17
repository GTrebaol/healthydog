// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.Gradle.GRADLE_VERSION}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.KOTLIN_VERSION}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }


}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    // Address https://github.com/gradle/gradle/issues/4823: Force parent project evaluation before sub-project evaluation for Kotlin build scripts
    if (gradle.startParameter.isConfigureOnDemand
        && buildscript.sourceFile?.extension?.toLowerCase() == "kts"
        && parent != rootProject) {
        generateSequence(parent) { project -> project.parent.takeIf { it != rootProject } }
            .forEach { evaluationDependsOn(it.path) }
    }
}



tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

