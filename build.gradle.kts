plugins { alias(libs.plugins.kotlin.jvm) }

group = "io.arrow-kt"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
  implementation(libs.arrow.core)
  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }

kotlin {
  jvmToolchain(8)
  explicitApi()
  compilerOptions {
    freeCompilerArgs.add("-Xcontext-receivers")
    optIn.add("kotlin.contracts.ExperimentalContracts")
  }
}
