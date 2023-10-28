package caios.android.pixiview.core.model.fanbox.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FanboxNewsLattersEntity(
    @SerialName("body")
    val body: List<Body>
) {
    @Serializable
    data class Body(
        @SerialName("body")
        val body: String,
        @SerialName("createdAt")
        val createdAt: String,
        @SerialName("creator")
        val creator: Creator,
        @SerialName("id")
        val id: String,
        @SerialName("isRead")
        val isRead: Boolean
    ) {
        @Serializable
        data class Creator(
            @SerialName("creatorId")
            val creatorId: String,
            @SerialName("user")
            val user: User
        ) {
            @Serializable
            data class User(
                @SerialName("iconUrl")
                val iconUrl: String,
                @SerialName("name")
                val name: String,
                @SerialName("userId")
                val userId: String
            )
        }
    }
}
