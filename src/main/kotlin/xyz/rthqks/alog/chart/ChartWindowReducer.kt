package xyz.rthqks.alog.chart

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import xyz.rthqks.alog.app.state.ChartWindowState
import xyz.rthqks.alog.intent.CloseWindow
import xyz.rthqks.alog.intent.Intent
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.model.AlogDocument
import xyz.rthqks.alog.settings.Setting
import xyz.rthqks.alog.usecase.*
import java.io.File

class ChartWindowReducer(
    parent: Reducer<*>,
    private val file: File,
    getFileContent: GetFileContent,
    parsePythonLiteral: ParsePythonLiteral,
    createAlogFromMap: CreateAlogFromMap,
    private val createChartStateFromAlog: CreateChartStateFromAlog,
    private val getSettings: GetSettings,
) : Reducer<ChartWindowState>(parent) {
    private val clockState = mutableStateOf("")
    private val titleState = mutableStateOf("")
    private val chartState = mutableStateOf(ChartState(emptyList(), emptyList(), emptyList()))
    private val coroutineScope = CoroutineScope(Job())
    override val state: ChartWindowState = ChartWindowState(titleState, chartState, clockState)

    init {
        coroutineScope.launch {
            val fileContent = getFileContent(file)
            val parsed = parsePythonLiteral(fileContent)
            createAlogFromMap(parsed)?.let { doc ->
                getSettings().collect {
                    updateState(doc, it)
                }
            }
        }
    }

    private fun updateState(doc: AlogDocument, setting: Setting) {
        val chart = createChartStateFromAlog(doc, setting)
        val title = "${doc.title} - ${file.name}"
        titleState.value = title
        chartState.value = chart
    }

    override fun handleIntent(intent: Intent) = when (intent) {
        is CloseWindow -> coroutineScope.cancel()
        else -> Unit
    }
}