package caios.android.pixiview.core.model.fanbox.id

import kotlinx.serialization.Serializable

@Serializable
data class CreatorId(val value: String) {
    override fun toString(): String = value
}
