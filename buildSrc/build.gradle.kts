plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven(url = "https://plugins.gradle.org/m2/")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

dependencies {
    // https://issuetracker.google.com/issues/166468915
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.0")
}
