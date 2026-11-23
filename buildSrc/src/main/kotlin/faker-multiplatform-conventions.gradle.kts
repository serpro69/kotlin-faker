/** Opt-in targets for portable libraries; domain modules adopt this after core is complete. */
plugins { id("faker-lib-conventions") }

val portableSettings = extensions.getByType<BuildSettings>()

kotlin {
    if (portableSettings.enableKotlinJs.get()) {
        js {
            browser { testTask { useKarma { useChromeHeadless() } } }
            nodejs()
        }
    }
    if (portableSettings.enableKotlinNative.get()) {
        iosArm64()
        iosSimulatorArm64()
    }

    sourceSets {
        commonTest.dependencies { implementation(kotlin("test")) }
        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
            runtimeOnly(libs.test.junit.jupiter.engine)
            runtimeOnly(libs.test.junit.platform.launcher)
        }
    }
}
