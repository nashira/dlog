package xyz.rthqks.alog.settings

import androidx.compose.runtime.mutableStateOf
import xyz.rthqks.alog.app.state.SettingsWindowState
import xyz.rthqks.alog.intent.ChangeSetting
import xyz.rthqks.alog.intent.Intent
import xyz.rthqks.alog.logic.Reducer

class SettingsWindowReducer(
    parent: Reducer<*>?,
    val settingsRepo: SettingsRepo,
) : Reducer<SettingsWindowState>(parent) {
    private val settingState = mutableStateOf<Setting>(BooleanSetting(false))
    override val state = SettingsWindowState(settingState)

    override fun handleIntent(intent: Intent) = when (intent) {
        is ChangeSetting -> {
            settingState.value = intent.setting
        }
        else -> Unit
    }
}