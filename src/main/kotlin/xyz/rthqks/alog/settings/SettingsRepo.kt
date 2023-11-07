package xyz.rthqks.alog.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepo {
    private val _setting = MutableStateFlow<Setting>(BooleanSetting(false))
    val setting: StateFlow<Setting> = _setting

    fun set(setting: Setting) {
        _setting.value = setting
    }
}