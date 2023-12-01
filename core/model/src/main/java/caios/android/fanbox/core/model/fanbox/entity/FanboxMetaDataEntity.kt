package caios.android.fanbox.core.model.fanbox.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FanboxMetaDataEntity(
    @SerialName("apiUrl")
    val apiUrl: String,
    @SerialName("context")
    val context: Context,
    @SerialName("csrfToken")
    val csrfToken: String,
    @SerialName("invitationsDisabled")
    val invitationsDisabled: Boolean,
    @SerialName("isOnCc")
    val isOnCc: Boolean,
    @SerialName("urlContext")
    val urlContext: UrlContext,
    @SerialName("wwwUrl")
    val wwwUrl: String,
) {
    @Serializable
    data class Context(
        @SerialName("privacyPolicy")
        val privacyPolicy: PrivacyPolicy,
        @SerialName("user")
        val user: User,
    ) {
        @Serializable
        data class PrivacyPolicy(
            @SerialName("policyUrl")
            val policyUrl: String,
            @SerialName("revisionHistoryUrl")
            val revisionHistoryUrl: String,
            @SerialName("shouldShowNotice")
            val shouldShowNotice: Boolean,
            @SerialName("updateDate")
            val updateDate: String,
        )

        @Serializable
        data class User(
            @SerialName("creatorId")
            val creatorId: String?,
            @SerialName("fanboxUserStatus")
            val fanboxUserStatus: Int,
            @SerialName("hasAdultContent")
            val hasAdultContent: Boolean?,
            @SerialName("hasUnpaidPayments")
            val hasUnpaidPayments: Boolean,
            @SerialName("iconUrl")
            val iconUrl: String?,
            @SerialName("isCreator")
            val isCreator: Boolean,
            @SerialName("isMailAddressOutdated")
            val isMailAddressOutdated: Boolean,
            @SerialName("isSupporter")
            val isSupporter: Boolean,
            @SerialName("lang")
            val lang: String,
            @SerialName("name")
            val name: String,
            @SerialName("planCount")
            val planCount: Int,
            @SerialName("showAdultContent")
            val showAdultContent: Boolean,
            @SerialName("userId")
            val userId: String?,
        )
    }

    @Serializable
    data class UrlContext(
        @SerialName("creatorOriginPattern")
        val creatorOriginPattern: String,
        @SerialName("host")
        val host: Host,
        @SerialName("rootOriginPattern")
        val rootOriginPattern: String,
        @SerialName("user")
        val user: User,
    ) {
        @Serializable
        data class Host(
            @SerialName("creatorId")
            val creatorId: String?,
        )

        @Serializable
        data class User(
            @SerialName("creatorId")
            val creatorId: String?,
            @SerialName("isLoggedIn")
            val isLoggedIn: Boolean,
        )
    }
}
