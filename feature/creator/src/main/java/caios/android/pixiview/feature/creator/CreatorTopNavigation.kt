package caios.android.pixiview.feature.creator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import caios.android.pixiview.core.model.fanbox.id.CreatorId
import caios.android.pixiview.core.model.fanbox.id.PostId
import caios.android.pixiview.core.ui.animation.NavigateAnimation

const val CreatorTopId = "creatorTopId"
const val CreatorTopIsPosts = "creatorTopIsPosts"
const val CreatorTopRoute = "creatorTop/{$CreatorTopId}/{$CreatorTopIsPosts}"

fun NavController.navigateToCreatorTop(creatorId: CreatorId, isPosts: Boolean = false) {
    this.navigate("creatorTop/$creatorId/$isPosts")
}

fun NavGraphBuilder.creatorTopScreen(
    navigateToPostDetail: (PostId) -> Unit,
    terminate: () -> Unit,
) {
    composable(
        route = CreatorTopRoute,
        arguments = listOf(
            navArgument(CreatorTopId) { type = NavType.StringType },
            navArgument(CreatorTopIsPosts) { type = NavType.BoolType },
        ),
        enterTransition = { NavigateAnimation.Horizontal.enter },
        exitTransition = { NavigateAnimation.Horizontal.exit },
        popEnterTransition = { NavigateAnimation.Horizontal.popEnter },
        popExitTransition = { NavigateAnimation.Horizontal.popExit },
    ) {
        CreatorTopRoute(
            modifier = Modifier.fillMaxSize(),
            creatorId = CreatorId(it.arguments?.getString(CreatorTopId) ?: ""),
            isPosts = it.arguments?.getBoolean(CreatorTopIsPosts) ?: false,
            navigateToPostDetail = navigateToPostDetail,
            terminate = terminate,
        )
    }
}
