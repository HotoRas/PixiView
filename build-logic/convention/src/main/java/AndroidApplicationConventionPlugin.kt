
import caios.android.fanbox.configureApplication
import caios.android.fanbox.configureKotlinAndroid
import caios.android.fanbox.libs
import caios.android.fanbox.version
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("kotlin-android")
                apply("kotlin-parcelize")
                apply("kotlinx-serialization")
                apply("project-report")
                apply("com.mikepenz.aboutlibraries.plugin")
                apply("com.google.devtools.ksp")
                apply("com.google.gms.google-services")
                apply("com.google.android.gms.oss-licenses-plugin")
                apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
            }

            extensions.configure<ApplicationExtension> {
                configureApplication()
                configureKotlinAndroid(this)

                defaultConfig.targetSdk = libs.version("targetSdk").toInt()
                buildFeatures.viewBinding = true
            }
        }
    }
}
