package caios.android.fanbox.core.billing.usecase

import caios.android.fanbox.core.billing.PurchaseSingleCommand
import caios.android.fanbox.core.billing.models.ProductDetails
import com.android.billingclient.api.Purchase

data class PurchaseConsumableResult(
    val command: PurchaseSingleCommand,
    val productDetails: ProductDetails,
    val purchase: Purchase,
)
