package caios.android.kanade.feature.welcome.top

import android.content.Context
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import caios.android.pixiview.core.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
@HiltViewModel
class WelcomeTopViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    fun setAgreedPrivacyPolicy() {
        viewModelScope.launch {
            userDataRepository.setAgreedPrivacyPolicy(true)
        }
    }

    fun setAgreedTermsOfService() {
        viewModelScope.launch {
            userDataRepository.setAgreedTermsOfService(true)
        }
    }
}
