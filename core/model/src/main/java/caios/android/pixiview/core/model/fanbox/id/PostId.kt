package caios.android.pixiview.core.model.fanbox.id

import kotlinx.serialization.Serializable

@Serializable
data class PostId(val value: String) {
    override fun toString(): String = value
}
