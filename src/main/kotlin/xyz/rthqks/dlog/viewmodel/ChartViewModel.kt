package xyz.rthqks.dlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.rthqks.dlog.io.AlogReplayClient
import xyz.rthqks.dlog.logic.*
import xyz.rthqks.dlog.logic.task.DeleteTask
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.state.ChartState
import java.io.File

class ChartViewModel(
    private val task: Task.ViewAlogChart,
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
    private val createAlogFromMap: CreateAlogFromMap,
    private val createChartStateFromAlog: CreateChartStateFromAlog,
    private val alogReplayClient: AlogReplayClient,
    private val createChartStateFromCapture: CreateChartStateFromCapture,
    private val windowClose: () -> Unit
) : ViewModel() {
    val replayState = alogReplayClient(task.fileName)
        .map(createChartStateFromCapture::invoke)
        .stateIn(
            coroutineScope, SharingStarted.Lazily, ChartState(
                emptyList(),
                emptyList(),
                emptyList()
            )
        )

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