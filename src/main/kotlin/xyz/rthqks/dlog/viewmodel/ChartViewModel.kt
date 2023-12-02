package xyz.rthqks.dlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import xyz.rthqks.dlog.logic.CreateAlogFromMap
import xyz.rthqks.dlog.logic.CreateChartStateFromAlog
import xyz.rthqks.dlog.logic.GetFileContent
import xyz.rthqks.dlog.logic.ParsePythonLiteral
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.state.ChartState
import java.io.File

class ChartViewModel(
    private val task: Task,
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
    private val createAlogFromMap: CreateAlogFromMap,
    private val createChartStateFromAlog: CreateChartStateFromAlog,
    private val windowClose: () -> Unit
) : ViewModel() {

    val titleState = mutableStateOf("")
    val chartState = mutableStateOf(
        ChartState(
            emptyList(),
            emptyList(),
            emptyList()
        )
    )

    init {
        val file = File(task.fileName)
        val content = getFileContent(file)
        val pythonLiteral = parsePythonLiteral(content)
        val alog = createAlogFromMap(file.name, pythonLiteral)
        alog?.let {
            chartState.value = createChartStateFromAlog(it)
            titleState.value = it.title
        }
    }

    fun onWindowClose() {
        close()
        windowClose()
    }
}