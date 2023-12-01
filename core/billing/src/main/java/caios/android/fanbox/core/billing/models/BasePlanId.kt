package caios.android.fanbox.core.billing.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasePlanId(val value: String) : Parcelable {
    override fun toString(): String = value
}
