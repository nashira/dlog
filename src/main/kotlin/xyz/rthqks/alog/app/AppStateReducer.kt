package xyz.rthqks.alog.app

import androidx.compose.runtime.mutableStateOf
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import xyz.rthqks.alog.app.state.*
import xyz.rthqks.alog.chart.ChartWindowReducer
import xyz.rthqks.alog.logic.*
import xyz.rthqks.alog.settings.SettingsWindowReducer

class AppStateReducer: Reducer<AppState>(), KoinScopeComponent {
    override val scope: Scope by lazy {
        createScope().also {
            it.declare(this as Reducer<*>)
            it.declare(FilePickerWindowReducer.Mode.Open)
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

        is FindFileToOpen -> windowsFlow.value += get<FilePickerWindowReducer>()
        is FindFileToReplay -> windowsFlow.value += get<FilePickerWindowReducer> {
            parametersOf(FilePickerWindowReducer.Mode.Replay)
        }

        is OpenChartWindow -> windowsFlow.value += get<ChartWindowReducer> {
            parametersOf(intent.alogDocument)
        }

        is ReplayChartWindow -> windowsFlow.value += get<ChartWindowReducer> {
            parametersOf(intent.alogDocument)
        }

        else -> Unit
    }

    private fun removeWindow(intent: CloseWindow) {
        windowsFlow.value = windowsFlow.value.filter { it.state != intent.window }
    }

    fun onApplicationExit(exitBlock: () -> Unit) {
        this.exitBlock = exitBlock
    }
}
