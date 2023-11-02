package xyz.rthqks.alog.logic

import androidx.compose.runtime.mutableStateOf
import xyz.rthqks.alog.intent.*
import xyz.rthqks.alog.state.AppState
import xyz.rthqks.alog.state.ChartWindowState
import xyz.rthqks.alog.state.FilePickerWindowState
import xyz.rthqks.alog.state.WindowState
import xyz.rthqks.alog.usecase.GetFileContent
import xyz.rthqks.alog.usecase.ParsePythonLiteral
import java.io.File

class AppStateReducer(
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
) : Reducer() {
    private var exitBlock: (() -> Unit)? = null
    private val selectFiles: (List<File>) -> Unit = { this(SelectFiles(it)) }

    private val windowsFlow = mutableStateOf(
        listOf<WindowState>(
            FilePickerWindowState
        )
    )

    val appState = AppState(windowsFlow)

    override operator fun invoke(intent: Intent) {
        println("intent: $intent")

        when (intent) {
            is ExitApplication -> intent.applicationScope.exitApplication()

            is RefreshData -> {}

            is SelectFiles -> {
                intent.files.firstOrNull()?.let { file ->
                    val fileContent = getFileContent(file)
                    val parsed = parsePythonLiteral(fileContent)
                    println(parsed)
                    windowsFlow.value += ChartWindowState
                }
            }

            is ShowFileSelector -> windowsFlow.value += FilePickerWindowState

            is CloseWindow -> {
                windowsFlow.value = windowsFlow.value.filter { it != intent.window }
            }
        }

        println(windowsFlow.value)
    }

    fun onApplicationExit(exitBlock: () -> Unit) {
        this.exitBlock = exitBlock
    }
}