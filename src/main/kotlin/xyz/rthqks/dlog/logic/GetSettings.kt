package xyz.rthqks.dlog.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import xyz.rthqks.dlog.repo.Settings
import xyz.rthqks.dlog.repo.SettingsRepo
import xyz.rthqks.dlog.state.SettingsState

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