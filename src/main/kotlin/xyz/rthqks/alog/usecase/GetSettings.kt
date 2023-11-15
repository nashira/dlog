package xyz.rthqks.alog.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import xyz.rthqks.alog.settings.repo.Settings
import xyz.rthqks.alog.settings.repo.SettingsRepo
import xyz.rthqks.alog.settings.state.SettingsState

class GetSettings(
    private val settingsRepo: SettingsRepo
) {

    operator fun invoke(scope: CoroutineScope): StateFlow<SettingsState> = settingsRepo
        .settings
        .drop(1)
        .map(::map)
        .stateIn(scope, SharingStarted.Eagerly, SettingsState())

    private fun map(settings: Settings): SettingsState = SettingsState()
}
