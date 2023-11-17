package caios.android.pixiview.feature.setting.theme

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import caios.android.pixiview.core.model.ScreenState
import caios.android.pixiview.core.model.ThemeColorConfig
import caios.android.pixiview.core.model.ThemeConfig
import caios.android.pixiview.core.model.UserData
import caios.android.pixiview.core.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
@HiltViewModel
class SettingThemeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val screenState = userDataRepository.userData.map {
        ScreenState.Idle(
            SettingThemeState(
                userData = it,
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ScreenState.Loading,
    )

    fun setThemeConfig(themeConfig: ThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setThemeConfig(themeConfig)
        }
    }

    fun setThemeColorConfig(themeColorConfig: ThemeColorConfig) {
        viewModelScope.launch {
            userDataRepository.setThemeColorConfig(themeColorConfig)
        }
    }

    fun setUseDynamicColor(useDynamicColor: Boolean) {
        viewModelScope.launch {
            userDataRepository.setUseDynamicColor(useDynamicColor)
        }
    }
}

@Stable
data class SettingThemeState(
    val userData: UserData,
)
