plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "io.ii.di"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

dependencies {
    implementation(projects.feature.projects.domain)
    implementation(projects.feature.projects.data)
    implementation(projects.feature.projects.presentation)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}