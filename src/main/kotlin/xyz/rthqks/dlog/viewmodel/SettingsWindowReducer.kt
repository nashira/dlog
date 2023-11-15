package xyz.rthqks.dlog.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope
import xyz.rthqks.dlog.state.SettingsWindowState
import xyz.rthqks.dlog.intent.ChangeSetting
import xyz.rthqks.dlog.intent.CloseWindow
import xyz.rthqks.dlog.intent.Intent
import xyz.rthqks.dlog.logic.GetSettings
import xyz.rthqks.dlog.logic.SetSetting

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
