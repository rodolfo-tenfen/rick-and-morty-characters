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
    implementation(project(":domain:character"))
    implementation(project(":data:page:datasource:remote"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.koin.core)
    implementation(libs.okhttp)
}
