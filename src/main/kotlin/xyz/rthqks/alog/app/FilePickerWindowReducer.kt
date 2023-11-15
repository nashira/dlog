package xyz.rthqks.alog.app

import xyz.rthqks.alog.app.state.FindFileWindowState
import xyz.rthqks.alog.logic.*
import xyz.rthqks.alog.usecase.CreateAlogFromMap
import xyz.rthqks.alog.usecase.GetFileContent
import xyz.rthqks.alog.usecase.ParsePythonLiteral
import java.io.File

class FilePickerWindowReducer(
    parent: Reducer<*>,
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
    private val createAlogFromMap: CreateAlogFromMap,
    private val mode: Mode = Mode.Open,
) : Reducer<FindFileWindowState>(parent) {
    override val state = FindFileWindowState

    override fun handleIntent(intent: Intent) = when (intent) {
        is SelectFiles -> if (mode == Mode.Replay) {
            replayFiles(intent.files)
        } else {
            openFiles(intent.files)
        }

        else -> Unit
    }

    private fun replayFiles(files: List<File>) {
        files.forEach { file ->
            val fileContent = getFileContent(file)
            val parsed = parsePythonLiteral(fileContent)
            createAlogFromMap(file.name, parsed)?.let {
                this(ReplayChartWindow(it))
            }
        }
    }

    private fun openFiles(files: List<File>) {
        files.forEach { file ->
            val fileContent = getFileContent(file)
            val parsed = parsePythonLiteral(fileContent)
            createAlogFromMap(file.name, parsed)?.let {
                this(OpenChartWindow(it))
            }
        }
    }

    enum class Mode {
        Open, Replay
    }
}
