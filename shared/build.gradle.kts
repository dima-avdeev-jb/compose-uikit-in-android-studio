plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version "1.3.0-alpha01-dev824"
}

kotlin {
    android()
    if (System.getProperty("os.arch") == "aarch64") {
        iosSimulatorArm64() {
            binaries.framework {
                baseName = "shared"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    } else {
        iosX64() {
            binaries.framework {
                baseName = "shared"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting

        val simTarget = if (System.getProperty("os.arch") == "aarch64") {
            val iosSimulatorArm64Main by getting
            iosSimulatorArm64Main
        } else {
            val iosX64Main by getting
            iosX64Main
        }

        val iosMain by creating {
            dependsOn(commonMain)
            simTarget.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.composeapp"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}