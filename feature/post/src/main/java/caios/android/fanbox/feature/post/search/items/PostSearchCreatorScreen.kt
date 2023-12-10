package caios.android.fanbox.feature.post.search.items

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import caios.android.fanbox.core.model.FanboxTag
import caios.android.fanbox.core.model.fanbox.FanboxCreatorDetail
import caios.android.fanbox.core.model.fanbox.id.CreatorId
import caios.android.fanbox.core.ui.LazyPagingItemsLoadContents
import caios.android.fanbox.core.ui.component.CreatorItem
import caios.android.fanbox.core.ui.extensition.drawVerticalScrollbar
import caios.android.fanbox.core.ui.theme.bold
import caios.android.fanbox.core.ui.view.PagingErrorSection
import caios.android.fanbox.feature.post.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@Composable
internal fun PostSearchCreatorScreen(
    pagingAdapter: LazyPagingItems<FanboxCreatorDetail>,
    suggestTags: ImmutableList<FanboxTag>,
    onClickCreator: (CreatorId) -> Unit,
    onClickTag: (String) -> Unit,
    onClickFollow: suspend (String) -> Result<Unit>,
    onClickUnfollow: suspend (String) -> Result<Unit>,
    onClickSupporting: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()

    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo }.collect {
            keyboardController?.hide()
        }
    }

    LazyPagingItemsLoadContents(
        modifier = modifier,
        lazyPagingItems = pagingAdapter,
        emptyMessageRes = R.string.error_no_data_search,
    ) {
        LazyColumn(
            modifier = Modifier.drawVerticalScrollbar(state),
            state = state,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (suggestTags.isNotEmpty()) {
                item {
                    TitleItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(R.string.common_tag),
                    )
                }

                item {
                    PostSearchSuggestTagsSection(
                        modifier = Modifier.fillMaxWidth(),
                        suggestTags = suggestTags,
                        onClickTag = onClickTag,
                    )
                }

                item {
                    TitleItem(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        title = stringResource(R.string.common_creator),
                    )
                }
            }

            items(
                count = pagingAdapter.itemCount,
                key = pagingAdapter.itemKey(),
                contentType = pagingAdapter.itemContentType(),
            ) { index ->
                pagingAdapter[index]?.let { creatorDetail ->
                    var isFollowed by rememberSaveable { mutableStateOf(creatorDetail.isFollowed) }

                    CreatorItem(
                        modifier = Modifier.fillMaxWidth(),
                        creatorDetail = creatorDetail,
                        isFollowed = isFollowed,
                        onClickCreator = onClickCreator,
                        onClickFollow = {
                            scope.launch {
                                isFollowed = true
                                isFollowed = onClickFollow.invoke(it).isSuccess
                            }
                        },
                        onClickUnfollow = {
                            scope.launch {
                                isFollowed = false
                                isFollowed = !onClickUnfollow.invoke(it).isSuccess
                            }
                        },
                        onClickSupporting = onClickSupporting,
                    )
                }
            }

            if (pagingAdapter.loadState.append is LoadState.Error) {
                item {
                    PagingErrorSection(
                        modifier = Modifier.fillMaxWidth(),
                        onRetry = { pagingAdapter.retry() },
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}

@Composable
private fun TitleItem(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = title.uppercase(),
        style = MaterialTheme.typography.bodyMedium.bold(),
        color = MaterialTheme.colorScheme.primary,
    )
}
