// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io")

    }
    dependencies {
       classpath (BuildPluginsDependencies.androidGradlePlugin)
        classpath (BuildPluginsDependencies.kotlinGradlePlugin)
        classpath (BuildPluginsDependencies.gmsPlayServicesPlugin)
        classpath (BuildPluginsDependencies.hiltGradlePlugin)
        classpath (BuildPluginsDependencies.navigationGradlePlugin)
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven ("https://jitpack.io")
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}