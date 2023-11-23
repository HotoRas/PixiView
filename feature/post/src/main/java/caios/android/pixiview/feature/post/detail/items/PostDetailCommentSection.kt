package caios.android.pixiview.feature.post.detail.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import caios.android.pixiview.core.model.fanbox.FanboxMetaData
import caios.android.pixiview.core.model.fanbox.FanboxPostDetail
import caios.android.pixiview.core.model.fanbox.FanboxUser
import caios.android.pixiview.core.model.fanbox.id.CommentId
import caios.android.pixiview.core.model.fanbox.id.PostId
import caios.android.pixiview.core.ui.theme.bold
import caios.android.pixiview.core.ui.theme.center
import caios.android.pixiview.feature.post.R
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.time.Duration
import java.time.OffsetDateTime

@Composable
internal fun PostDetailCommentSection(
    postDetail: FanboxPostDetail,
    metaData: FanboxMetaData,
    onClickLoadMore: (PostId, Int) -> Unit,
    onClickCommentLike: (CommentId) -> Unit,
    onClickCommentReply: (String, CommentId, CommentId) -> Unit,
    onClickCommentDelete: (CommentId) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isShowCommentEditor by rememberSaveable { mutableStateOf(false) }
    var latestComment by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(postDetail.commentList) {
        if (isShowCommentEditor) {
            val comments = postDetail.commentList.contents.flatMap { comment -> listOf(comment) + comment.replies }
            isShowCommentEditor = !comments.any { comment -> comment.user.name == metaData.context.user.name && comment.body == latestComment }
        }
    }

    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.post_detail_comment_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            IconButton(onClick = { isShowCommentEditor = !isShowCommentEditor }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        }

        AnimatedVisibility(visible = isShowCommentEditor) {
            CommentEditor(
                modifier = Modifier
                    .animateContentSize()
                    .padding(top = 24.dp)
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                parentCommentId = CommentId("0"),
                rootCommentId = CommentId("0"),
                metaData = metaData,
                onClickCommentReply = { body, parentCommentId, rootCommentId ->
                    latestComment = body
                    onClickCommentReply.invoke(body, parentCommentId, rootCommentId)
                }
            )
        }

        if (postDetail.commentList.contents.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                for (comment in postDetail.commentList.contents) {
                    CommentItem(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        metaData = metaData,
                        comment = comment,
                        onClickCommentLike = onClickCommentLike,
                        onClickCommentReply = { body, parentCommentId, rootCommentId ->
                            latestComment = body
                            onClickCommentReply.invoke(body, parentCommentId, rootCommentId)
                        },
                        onClickCommentDelete = onClickCommentDelete,
                    )
                }
            }
        } else {
            Text(
                modifier = Modifier
                    .alpha(if (!isShowCommentEditor) 1f else 0f)
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.post_detail_comment_empty),
                style = MaterialTheme.typography.bodyMedium.center(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        if (postDetail.commentList.offset != null) {
            Button(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = { onClickLoadMore.invoke(postDetail.id, postDetail.commentList.offset!!) },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.common_see_more),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: FanboxPostDetail.Comment.CommentItem,
    metaData: FanboxMetaData,
    onClickCommentLike: (CommentId) -> Unit,
    onClickCommentReply: (String, CommentId, CommentId) -> Unit,
    onClickCommentDelete: (CommentId) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isShowReplyEditor by rememberSaveable(comment) { mutableStateOf(false) }
    var isLiked by rememberSaveable { mutableStateOf(comment.isLiked) }
    val createdDateBefore = rememberSaveable { (Duration.between(comment.createdDatetime, OffsetDateTime.now()).toDays()) }
    val likeColor = if (isLiked) Color(0xffe0405e) else MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .error(R.drawable.im_default_user)
                .data(comment.user.iconUrl)
                .build(),
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(),
        ) {
            Text(
                text = comment.user.name,
                style = MaterialTheme.typography.bodyMedium.bold(),
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = comment.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.unit_day_before, createdDateBefore),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            if (!isLiked) {
                                isLiked = true
                                onClickCommentLike.invoke(comment.id)
                            }
                        }
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Favorite,
                        tint = likeColor,
                        contentDescription = null,
                    )

                    Text(
                        text = (comment.likeCount + if (isLiked) 1 else 0).toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = likeColor,
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { isShowReplyEditor = !isShowReplyEditor }
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Message,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null,
                    )

                    Text(
                        text = stringResource(R.string.post_detail_comment_reply),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                if (comment.isOwn) {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { onClickCommentDelete.invoke(comment.id) }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Default.Delete,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentDescription = null,
                        )

                        Text(
                            text = stringResource(R.string.common_delete),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            for (reply in comment.replies) {
                CommentItem(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    metaData = metaData,
                    comment = reply,
                    onClickCommentLike = onClickCommentLike,
                    onClickCommentReply = onClickCommentReply,
                    onClickCommentDelete = onClickCommentDelete,
                )
            }

            AnimatedVisibility(visible = isShowReplyEditor) {
                CommentEditor(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    parentCommentId = comment.id,
                    rootCommentId = comment.rootCommentId,
                    metaData = metaData,
                    onClickCommentReply = onClickCommentReply,
                )
            }
        }
    }
}

@Composable
private fun CommentEditor(
    parentCommentId: CommentId,
    rootCommentId: CommentId,
    metaData: FanboxMetaData,
    onClickCommentReply: (String, CommentId, CommentId) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isError by rememberSaveable { mutableStateOf(false) }
    var value by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(value) {
        isError = value.length > 1000
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .error(R.drawable.im_default_user)
                .data(metaData.context.user.iconUrl)
                .build(),
            contentDescription = null,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { value = it },
                isError = isError,
                supportingText = {
                    Text("${value.length} / 1000")
                },
            )

            Button(
                modifier = Modifier.align(Alignment.End),
                enabled = !isError,
                onClick = { onClickCommentReply.invoke(value, parentCommentId, if (rootCommentId.value != "0") rootCommentId else parentCommentId) },
            ) {
                Text(text = stringResource(R.string.post_detail_comment_reply))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommentItemPreview() {
    CommentItem(
        comment = FanboxPostDetail.Comment.CommentItem(
            body = "またサインこすさんのイラストが見れるとは…！\nありがとうございます！",
            createdDatetime = OffsetDateTime.now(),
            id = CommentId(""),
            isLiked = false,
            isOwn = false,
            likeCount = 0,
            parentCommentId = CommentId(""),
            rootCommentId = CommentId(""),
            user = FanboxUser.dummy(),
            replies = emptyList(),
        ),
        metaData = FanboxMetaData.dummy(),
        onClickCommentLike = {},
        onClickCommentReply = { _, _, _ -> },
        onClickCommentDelete = {},
    )
}
