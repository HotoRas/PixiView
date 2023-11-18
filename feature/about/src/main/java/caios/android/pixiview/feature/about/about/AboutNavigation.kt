package caios.android.pixiview.feature.about.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import caios.android.pixiview.core.model.Version
import caios.android.pixiview.core.ui.animation.NavigateAnimation
import kotlinx.collections.immutable.ImmutableList

const val AboutRoute = "about"

fun NavController.navigateToAbout() {
    this.navigate(AboutRoute)
}

fun NavGraphBuilder.aboutScreen(
    navigateToVersionHistory: (ImmutableList<Version>) -> Unit,
    navigateToDonate: () -> Unit,
    terminate: () -> Unit,
) {
    composable(
        route = AboutRoute,
        enterTransition = { NavigateAnimation.Horizontal.enter },
        exitTransition = { NavigateAnimation.Horizontal.exit },
        popEnterTransition = { NavigateAnimation.Horizontal.popEnter },
        popExitTransition = { NavigateAnimation.Horizontal.popExit },
    ) {
        AboutRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToVersionHistory = navigateToVersionHistory,
            navigateToDonate = navigateToDonate,
            terminate = terminate,
        )
    }
}
