package caios.android.pixiview.feature.post.search

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import caios.android.pixiview.core.common.util.suspendRunCatching
import caios.android.pixiview.core.model.fanbox.FanboxCreatorDetail
import caios.android.pixiview.core.model.fanbox.FanboxPost
import caios.android.pixiview.core.model.fanbox.id.CreatorId
import caios.android.pixiview.core.repository.FanboxRepository
import caios.android.pixiview.core.ui.extensition.emptyPaging
import caios.android.pixiview.feature.post.search.paging.PostSearchCreatorPagingSource
import caios.android.pixiview.feature.post.search.paging.PostSearchTagPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

@HiltViewModel
class PostSearchViewModel @Inject constructor(
    private val fanboxRepository: FanboxRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        PostSearchUiState(
            query = "",
            creatorPaging = emptyPaging(),
            tagPaging = emptyPaging(),
            postPaging = emptyPaging(),
        )
    )

    val uiState = _uiState.asStateFlow()

    fun search(query: PostSearchQuery) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(query = buildQuery(query))

            when (query.mode) {
                PostSearchMode.Creator -> {
                    Pager(
                        config = PagingConfig(pageSize = 10),
                        initialKey = null,
                        pagingSourceFactory = {
                            PostSearchCreatorPagingSource(fanboxRepository, query.creatorQuery.orEmpty())
                        },
                    ).flow.cachedIn(viewModelScope).also {
                        _uiState.value = uiState.value.copy(creatorPaging = it)
                    }
                }
                PostSearchMode.Tag -> {
                    Pager(
                        config = PagingConfig(pageSize = 10),
                        initialKey = null,
                        pagingSourceFactory = {
                            PostSearchTagPagingSource(fanboxRepository, query.creatorId, query.tag.orEmpty())
                        },
                    ).flow.cachedIn(viewModelScope).also {
                        _uiState.value = uiState.value.copy(tagPaging = it)
                    }
                }
                PostSearchMode.Post -> {
                    Pager(
                        config = PagingConfig(pageSize = 10),
                        initialKey = null,
                        pagingSourceFactory = {
                            PostSearchTagPagingSource(fanboxRepository, query.creatorId, query.postQuery.orEmpty())
                        },
                    ).flow.cachedIn(viewModelScope).also {
                        _uiState.value = uiState.value.copy(postPaging = it)
                    }
                }
                PostSearchMode.Unknown -> {
                    _uiState.value = uiState.value.copy(
                        creatorPaging = emptyPaging(),
                        tagPaging = emptyPaging(),
                        postPaging = emptyPaging(),
                    )
                }
            }
        }
    }

    suspend fun follow(creatorUserId: String): Result<Unit> {
        return suspendRunCatching {
            fanboxRepository.followCreator(creatorUserId)
        }
    }

    suspend fun unfollow(creatorUserId: String): Result<Unit> {
        return suspendRunCatching {
            fanboxRepository.unfollowCreator(creatorUserId)
        }
    }
}

@Stable
data class PostSearchUiState(
    val query: String,
    val creatorPaging: Flow<PagingData<FanboxCreatorDetail>>,
    val tagPaging: Flow<PagingData<FanboxPost>>,
    val postPaging: Flow<PagingData<FanboxPost>>,
)

enum class PostSearchMode {
    Creator,
    Tag,
    Post,
    Unknown,
}

@Serializable
data class PostSearchQuery(
    val mode: PostSearchMode,
    val creatorId: CreatorId?,
    val creatorQuery: String?,
    val postQuery: String?,
    val tag: String?,
)
