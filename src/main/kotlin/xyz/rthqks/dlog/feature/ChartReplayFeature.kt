package xyz.rthqks.dlog.feature

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import xyz.rthqks.dlog.io.AlogReplayClient
import xyz.rthqks.dlog.logic.CreateChartStateFromCapture
import xyz.rthqks.dlog.logic.task.CreateTask
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.state.ChartState

class ChartReplayFeature(
    private val task: Task,
    private val alogReplayClient: AlogReplayClient,
    private val createChartStateFromCapture: CreateChartStateFromCapture,
    private val createTask: CreateTask,
    private val windowClose: () -> Unit,
) : Feature() {
    val replayState = alogReplayClient(task.fileName ?: error("fileName is null"))
        .map(createChartStateFromCapture::invoke)
        .onCompletion { println("replay complete") }
        .stateIn(
            coroutineScope, SharingStarted.Lazily, ChartState(
                emptyList(),
                emptyList(),
                emptyList()
            )
        )

    val titleState = mutableStateOf(task.fileName ?: "")

    fun onWindowClose() {
        close()
        windowClose()
    }

    fun onSettingsOpen() {
        coroutineScope.launch { createTask(Task(-1, Task.Type.ReplaySettings)) }
    }
}
