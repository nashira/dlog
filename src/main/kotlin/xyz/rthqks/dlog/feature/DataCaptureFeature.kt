package xyz.rthqks.dlog.feature

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import xyz.rthqks.dlog.intent.Intent
import xyz.rthqks.dlog.io.WebsocketConfig
import xyz.rthqks.dlog.logic.capture.OpenDataCapture
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.state.ChartState

class DataCaptureFeature(
    private val task: Task,
    private val openDataCapture: OpenDataCapture,
    private val windowClose: () -> Unit,
) : Feature() {
    val chartState = mutableStateOf(
        ChartState(
            emptyList(),
            emptyList(),
            emptyList()
        )
    )

    val clientState = mutableStateOf(Unit)

    val titleState = mutableStateOf("Data Capture")


    fun onWindowClose() {
        close()
        windowClose()
    }

    fun handle(intent: DataCaptureIntent) {
        when (intent) {
            OpenDataCapture -> coroutineScope.launch {
                task.fileName?.let { openDataCapture(WebsocketConfig(it)) }
            }
        }
    }
}

sealed class DataCaptureIntent : Intent
data object OpenDataCapture: DataCaptureIntent()


