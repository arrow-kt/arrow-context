import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins { alias(libs.plugins.kotlin.jvm) }

group = "io.arrow-kt"

version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
  implementation(libs.arrow.core)
  implementation(libs.arrow.fx.coroutines)
  testImplementation(libs.coroutines.test)
  testImplementation(libs.kotlin.test)
  testImplementation(libs.kotest.assertionsCore)
  testImplementation(libs.kotest.property)
}

tasks.test { useJUnitPlatform() }

kotlin {
  jvmToolchain(8)
  explicitApi()
  compilerOptions {
    languageVersion = KotlinVersion.KOTLIN_2_0
    freeCompilerArgs.add("-Xcontext-receivers")
    freeCompilerArgs.add("-Xallow-kotlin-package")
    optIn.add("kotlin.contracts.ExperimentalContracts")
  }
}
