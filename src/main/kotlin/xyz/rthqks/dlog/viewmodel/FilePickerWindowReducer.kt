package xyz.rthqks.dlog.viewmodel

import xyz.rthqks.dlog.intent.Intent
import xyz.rthqks.dlog.intent.OpenChartWindow
import xyz.rthqks.dlog.intent.ReplayChartWindow
import xyz.rthqks.dlog.intent.SelectFiles
import xyz.rthqks.dlog.state.FindFileWindowState
import xyz.rthqks.dlog.logic.CreateAlogFromMap
import xyz.rthqks.dlog.logic.GetFileContent
import xyz.rthqks.dlog.logic.ParsePythonLiteral
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
