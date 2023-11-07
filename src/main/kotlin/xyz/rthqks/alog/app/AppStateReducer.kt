package xyz.rthqks.alog.app

import androidx.compose.runtime.mutableStateOf
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import xyz.rthqks.alog.app.state.AppState
import xyz.rthqks.alog.app.state.ChartWindowState
import xyz.rthqks.alog.app.state.FilePickerWindowReducer
import xyz.rthqks.alog.app.state.WindowState
import xyz.rthqks.alog.chart.ChartWindowReducer
import xyz.rthqks.alog.intent.*
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.settings.SettingsWindowReducer
import xyz.rthqks.alog.usecase.CreateAlogFromMap
import xyz.rthqks.alog.usecase.CreateChartStateFromAlog
import xyz.rthqks.alog.usecase.GetFileContent
import xyz.rthqks.alog.usecase.ParsePythonLiteral

class AppStateReducer(
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
    private val createAlogFromMap: CreateAlogFromMap,
    private val createChartStateFromAlog: CreateChartStateFromAlog,
) : Reducer<AppState>(), KoinScopeComponent {
    override val scope: Scope by lazy { createScope() }

    private var exitBlock: (() -> Unit)? = null

    private val windowsFlow = mutableStateOf(
        listOf<Reducer<out WindowState>>(
            // need to explicitly pass `parent` because
            // this is parent and this being created
            get<FilePickerWindowReducer> { parametersOf(this) }
        )
    )

    override val state = AppState(windowsFlow)

    override fun handleIntent(intent: Intent) = when (intent) {
        is CloseWindow -> removeWindow(intent)

        ShowEditSettings -> windowsFlow.value += get<SettingsWindowReducer>()

        is ShowFileSelector -> windowsFlow.value += get<FilePickerWindowReducer>()

        is SelectFiles -> intent.files.forEach { file ->
            val fileContent = getFileContent(file)
            val parsed = parsePythonLiteral(fileContent)
            createAlogFromMap(parsed)?.let { doc ->
                val chartState = createChartStateFromAlog(doc)
                val title = "${doc.title} - ${file.name}"
                windowsFlow.value += get<ChartWindowReducer> {
                    parametersOf(ChartWindowState(title, chartState))
                }
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
