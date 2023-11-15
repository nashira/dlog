package xyz.rthqks.alog.settings

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope
import xyz.rthqks.alog.app.state.SettingsWindowState
import xyz.rthqks.alog.logic.ChangeSetting
import xyz.rthqks.alog.logic.CloseWindow
import xyz.rthqks.alog.logic.Intent
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
    private val coroutineScope = CoroutineScope(Job())
    override val state = SettingsWindowState(
        getSettings(coroutineScope)
    )


    override fun handleIntent(intent: Intent) = when (intent) {
        is ChangeSetting -> {
            setSettings(intent.setting)
        }

        is CloseWindow -> coroutineScope.cancel()

        else -> Unit
    }
}
