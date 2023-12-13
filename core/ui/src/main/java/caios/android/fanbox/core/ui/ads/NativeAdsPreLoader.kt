package caios.android.fanbox.core.ui.ads

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import caios.android.fanbox.core.common.PixiViewConfig
import caios.android.fanbox.core.common.di.ApplicationScope
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Stable
data class PreLoadedNativeAd(
    val key: Int = 0,
    val ad: NativeAd,
    val date: LocalDateTime,
)

@Stable
@Singleton
class NativeAdsPreLoader @Inject constructor(
    @ApplicationScope private val scope: CoroutineScope,
    @ApplicationContext context: Context,
    pixiViewConfig: PixiViewConfig,
) {
    private var preloadedNativeAds: MutableList<PreLoadedNativeAd> = mutableListOf()
    private var adLoader: AdLoader
    private var key = 0

    init {
        adLoader = AdLoader.Builder(context, pixiViewConfig.adMobNativeAdUnitId)
            .forNativeAd { nativeAd ->
                preloadedNativeAds.add(
                    PreLoadedNativeAd(
                        key = key,
                        ad = nativeAd,
                        date = LocalDateTime.now(),
                    )
                )

                key++
            }
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()
    }

    @SuppressLint("MissingPermission")
    fun preloadAd() {
        if (adLoader.isLoading) return

        removeExpiredAds()

        val numberOfAds = NUMBER_OF_PRELOAD_ADS - preloadedNativeAds.count()

        if (numberOfAds > 0) {
            adLoader.loadAds(AdRequest.Builder().build(), numberOfAds)
        }
    }

    fun getKey(): Int? {
        removeExpiredAds()

        val first = preloadedNativeAds.firstOrNull()

        return if (first != null) {
            // ここでプリロードしてしまうと、フリークエンシーキャップを設定している場合の効果がなくなる.
            // 従って、広告を表示する少し前にプリロードするのが望ましい
            // preloadAd()

            if (preloadedNativeAds.size < 1) {
                scope.launch { preloadAd() }
            }

            first.key
        } else {
            scope.launch { preloadAd() }
            null
        }
    }

    fun getAd(key: Int): NativeAd? {
        return preloadedNativeAds.firstOrNull { it.key == key }?.ad
    }

    fun popAd(key: Int) {
        preloadedNativeAds.removeIf { it.key == key }
    }

    /** 期限切れのNativeAdを削除する */
    private fun removeExpiredAds() {
        val adLimitDate = LocalDateTime.now().minusHours(1)
        preloadedNativeAds = preloadedNativeAds.filter { it.date > adLimitDate }.toMutableList()
    }

    companion object {
        const val NUMBER_OF_PRELOAD_ADS = 3

        @Composable
        fun dummy() = NativeAdsPreLoader(
            scope = CoroutineScope(Dispatchers.IO),
            context = LocalContext.current,
            pixiViewConfig = PixiViewConfig.dummy()
        )
    }
}
