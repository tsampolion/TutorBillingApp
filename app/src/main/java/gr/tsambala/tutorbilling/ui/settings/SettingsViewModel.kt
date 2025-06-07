package gr.tsambala.tutorbilling.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.tsambala.tutorbilling.data.settings.AppSettings
import gr.tsambala.tutorbilling.data.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    init {
        viewModelScope.launch {
            repository.settings.collectLatest { _settings.value = it }
        }
    }

    fun updateCurrencySymbol(symbol: String) {
        viewModelScope.launch { repository.setCurrencySymbol(symbol) }
    }

    fun updateRounding(decimals: Int) {
        viewModelScope.launch { repository.setRounding(decimals) }
    }

    fun updateDarkTheme(enabled: Boolean) {
        viewModelScope.launch { repository.setDarkTheme(enabled) }
    }
}
