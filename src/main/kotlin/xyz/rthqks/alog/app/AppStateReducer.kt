package xyz.rthqks.alog.app

import androidx.compose.runtime.mutableStateOf
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import xyz.rthqks.alog.app.state.AppState
import xyz.rthqks.alog.app.state.WindowState
import xyz.rthqks.alog.chart.ChartWindowReducer
import xyz.rthqks.alog.intent.*
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.settings.SettingsWindowReducer

class AppStateReducer : Reducer<AppState>(), KoinScopeComponent {
    override val scope: Scope by lazy {
        createScope().apply {
            declare(this@AppStateReducer as Reducer<*>)
        }
    }

    private var exitBlock: (() -> Unit)? = null

    private val windowsFlow = mutableStateOf(
        listOf<Reducer<out WindowState>>(
            get<FilePickerWindowReducer>()
        )
    )

    override val state = AppState(windowsFlow)

    override fun handleIntent(intent: Intent) = when (intent) {
        is CloseWindow -> removeWindow(intent)

        ShowEditSettings -> windowsFlow.value += get<SettingsWindowReducer>()

        is ShowFileSelector -> windowsFlow.value += get<FilePickerWindowReducer>()

        is SelectFiles -> intent.files.forEach { file ->
            windowsFlow.value += get<ChartWindowReducer> {
                parametersOf(file)
            }
        }

        else -> Unit
    }.also { println("intent $intent") }

    private fun removeWindow(intent: CloseWindow) {
        windowsFlow.value = windowsFlow.value.filter { it.state != intent.window }
    }

    fun onApplicationExit(exitBlock: () -> Unit) {
        this.exitBlock = exitBlock
    }
}
