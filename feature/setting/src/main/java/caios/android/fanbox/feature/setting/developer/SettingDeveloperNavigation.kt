package caios.android.fanbox.feature.setting.developer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import caios.android.fanbox.core.ui.extensition.navigateWithLog

const val SettingDeveloperRoute = "SettingDeveloper"

fun NavController.navigateToSettingDeveloper() {
    this.navigateWithLog(SettingDeveloperRoute)
}

fun NavGraphBuilder.settingDeveloperDialog(
    terminate: () -> Unit,
) {
    dialog(
        route = SettingDeveloperRoute,
    ) {
        SettingDeveloperDialog(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp)),
            terminate = terminate,
        )
    }
}
