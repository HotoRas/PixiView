package caios.android.pixiview.feature.creator.top.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import caios.android.pixiview.core.model.fanbox.FanboxPost
import caios.android.pixiview.core.model.fanbox.id.CreatorId
import caios.android.pixiview.core.model.fanbox.id.PostId
import caios.android.pixiview.core.ui.component.PostItem
import caios.android.pixiview.core.ui.extensition.drawVerticalScrollbar

@Composable
internal fun CreatorTopPostsScreen(
    state: LazyListState,
    pagingAdapter: LazyPagingItems<FanboxPost>,
    onClickPost: (PostId) -> Unit,
    onClickCreator: (CreatorId) -> Unit,
    onClickPlanList: (CreatorId) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.drawVerticalScrollbar(state),
        state = state,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = pagingAdapter.itemCount,
            key = pagingAdapter.itemKey { it.id.value },
            contentType = pagingAdapter.itemContentType(),
        ) { index ->
            pagingAdapter[index]?.let { post ->
                PostItem(
                    modifier = Modifier.fillMaxWidth(),
                    post = post,
                    onClickPost = { if (!post.isRestricted) onClickPost.invoke(it) },
                    onClickCreator = onClickCreator,
                    onClickPlanList = onClickPlanList,
                )
            }
        }
    }
}
