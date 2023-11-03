package xyz.rthqks.alog.logic

import androidx.compose.runtime.mutableStateOf
import xyz.rthqks.alog.intent.*
import xyz.rthqks.alog.state.AppState
import xyz.rthqks.alog.state.ChartWindowState
import xyz.rthqks.alog.state.FilePickerWindowState
import xyz.rthqks.alog.state.WindowState
import xyz.rthqks.alog.usecase.CreateAlogFromMap
import xyz.rthqks.alog.usecase.CreateChartStateFromAlog
import xyz.rthqks.alog.usecase.GetFileContent
import xyz.rthqks.alog.usecase.ParsePythonLiteral

class AppStateReducer(
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
    private val createAlogFromMap: CreateAlogFromMap,
    private val createChartStateFromAlog: CreateChartStateFromAlog,
) : Reducer() {
    private var exitBlock: (() -> Unit)? = null

    private val windowsFlow = mutableStateOf(
        listOf<WindowState>(
            FilePickerWindowState
        )
    )

    val appState = AppState(windowsFlow)

    override operator fun invoke(intent: Intent) {
        println("intent ${intent::class.simpleName}")
        when (intent) {
            is RefreshData -> {}

            is SelectFiles -> intent.files.forEach { file ->
                val fileContent = getFileContent(file)
                val parsed = parsePythonLiteral(fileContent)
                createAlogFromMap(parsed)?.let { doc ->
                    val chartState = createChartStateFromAlog(doc)
                    windowsFlow.value += ChartWindowState(chartState)
                }
            }

            is ShowFileSelector -> windowsFlow.value += FilePickerWindowState

            is CloseWindow -> {
                removeWindow(intent)
            }
        }
    }

    private fun removeWindow(intent: CloseWindow) {
        windowsFlow.value = windowsFlow.value.filter { it != intent.window }
    }

    fun onApplicationExit(exitBlock: () -> Unit) {
        this.exitBlock = exitBlock
    }
}