package caios.android.pixiview.core.model.fanbox

import androidx.core.net.toUri
import caios.android.pixiview.core.model.fanbox.id.PostId
import java.time.OffsetDateTime

data class FanboxPostDetail(
    val id: PostId,
    val title: String,
    val body: Body,
    val coverImageUrl: String?,
    val commentCount: Int,
    val commentList: Comment,
    val excerpt: String,
    val feeRequired: Int,
    val hasAdultContent: Boolean,
    val imageForShare: String,
    val isLiked: Boolean,
    var isBookmarked: Boolean,
    val isRestricted: Boolean,
    val likeCount: Int,
    val tags: List<String>,
    val updatedDatetime: OffsetDateTime,
    val publishedDatetime: OffsetDateTime,
    val nextPost: OtherPost?,
    val prevPost: OtherPost?,
    val user: FanboxUser,
) {
    val browserUri get() = "https://www.fanbox.cc/@${user.creatorId}/posts/$id".toUri()

    sealed interface Body {
        val imageItems get() = when (this) {
            is Article -> blocks.filterIsInstance<Article.Block.Image>().map { it.item }
            is Image -> images
            is File -> emptyList()
            is Unknown -> emptyList()
        }

        val fileItems get() = when (this) {
            is Article -> blocks.filterIsInstance<Article.Block.File>().map { it.item }
            is Image -> emptyList()
            is File -> files
            is Unknown -> emptyList()
        }

        data class Article(val blocks: List<Block>) : Body {
            sealed interface Block {
                data class Text(val text: String) : Block

                data class Image(val item: ImageItem) : Block

                data class File(val item: FileItem) : Block

                data class Link(
                    val html: String?,
                    val post: FanboxPost?,
                ) : Block
            }
        }

        data class Image(
            val text: String,
            val images: List<ImageItem>,
        ) : Body

        data class File(
            val text: String,
            val files: List<FileItem>,
        ) : Body

        data object Unknown : Body
    }

    data class Comment(
        val items: List<CommentItem>,
        val nextUrl: String?,
    ) {
        data class CommentItem(
            val body: String,
            val createdDatetime: OffsetDateTime,
            val id: String,
            val isLiked: Boolean,
            val isOwn: Boolean,
            val likeCount: Int,
            val parentCommentId: String,
            val rootCommentId: String,
            val user: FanboxUser,
        )
    }

    data class OtherPost(
        val id: PostId,
        val title: String,
        val publishedDatetime: OffsetDateTime,
    )

    data class ImageItem(
        val id: String,
        val postId: PostId,
        val extension: String,
        val originalUrl: String,
        val thumbnailUrl: String,
        val aspectRatio: Float,
    )

    data class FileItem(
        val id: String,
        val postId: PostId,
        val name: String,
        val extension: String,
        val size: Long,
        val url: String,
    )
}
