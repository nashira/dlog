package xyz.rthqks.alog.logic

import androidx.compose.runtime.mutableStateOf
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.get
import org.koin.core.scope.Scope
import xyz.rthqks.alog.intent.*
import xyz.rthqks.alog.model.BooleanSetting
import xyz.rthqks.alog.model.Setting
import xyz.rthqks.alog.state.*
import xyz.rthqks.alog.usecase.CreateAlogFromMap
import xyz.rthqks.alog.usecase.CreateChartStateFromAlog
import xyz.rthqks.alog.usecase.GetFileContent
import xyz.rthqks.alog.usecase.ParsePythonLiteral

class AppStateReducer(
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
    private val createAlogFromMap: CreateAlogFromMap,
    private val createChartStateFromAlog: CreateChartStateFromAlog,
) : Reducer(), KoinScopeComponent {
    override val scope: Scope by lazy { createScope() }

    private var exitBlock: (() -> Unit)? = null

    private val windowsFlow = mutableStateOf(
        listOf<WindowState>(
            FilePickerWindowState
        )
    )

    val appState = AppState(windowsFlow)

    override fun handleIntent(intent: Intent) = when (intent) {
        is SelectFiles -> intent.files.forEach { file ->
            val fileContent = getFileContent(file)
            val parsed = parsePythonLiteral(fileContent)
            createAlogFromMap(parsed)?.let { doc ->
                val chartState = createChartStateFromAlog(doc)
                windowsFlow.value += ChartWindowState(chartState)
            }
        }
        is ShowFileSelector -> windowsFlow.value += FilePickerWindowState
        ShowEditSettings -> {
            windowsFlow.value += SettingsWindowState("Settings", BooleanSetting(false))
        }

        is CloseWindow -> {
            removeWindow(intent)
        }

        else -> Unit
    }

    private fun removeWindow(intent: CloseWindow) {
        windowsFlow.value = windowsFlow.value.filter { it != intent.window }
    }

    fun onApplicationExit(exitBlock: () -> Unit) {
        this.exitBlock = exitBlock
    }
}
