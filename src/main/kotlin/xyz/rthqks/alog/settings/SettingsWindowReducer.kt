package xyz.rthqks.alog.settings

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope
import xyz.rthqks.alog.app.state.SettingsWindowState
import xyz.rthqks.alog.intent.ChangeSetting
import xyz.rthqks.alog.intent.Intent
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.usecase.GetSettings
import xyz.rthqks.alog.usecase.SetSetting

class SettingsWindowReducer(
    parent: Reducer<*>?,
    getSettings: GetSettings,
    private val setSettings: SetSetting
) : Reducer<SettingsWindowState>(parent), KoinScopeComponent {
    override val scope: Scope by lazy {
        createScope()
    }
    private val settingState = getSettings()
    override val state = SettingsWindowState(settingState)

    override fun handleIntent(intent: Intent) = when (intent) {
        is ChangeSetting -> {
            setSettings(intent.setting)
        }

        else -> Unit
    }
}
