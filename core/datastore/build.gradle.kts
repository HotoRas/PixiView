plugins {
    id("pixiview.library")
    id("pixiview.detekt")
    id("pixiview.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "caios.android.pixiview.core.datastore"
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.androidx.datastore)
    implementation(libs.protobuf.kotlin.lite)
}
