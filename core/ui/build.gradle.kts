plugins {
    id("kanade.library")
    id("kanade.library.compose")
    id("kanade.hilt")
    id("kanade.detekt")
}

android {
    namespace = "caios.android.pixiview.core.ui"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:repository"))
    implementation(project(":core:datastore"))

    implementation(libs.bundles.ui.implementation)
    kapt(libs.bundles.ui.kapt)

    implementation(libs.androidx.palette)
    implementation(libs.reorderble.compose)
}
