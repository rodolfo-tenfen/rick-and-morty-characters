plugins {
    alias(libs.plugins.kotlin.jvm)

    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.ktlint.gradle)
    alias(libs.plugins.kotlinter)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
