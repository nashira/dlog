package xyz.rthqks.dlog.feature

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import xyz.rthqks.dlog.intent.Intent
import xyz.rthqks.dlog.io.WebsocketConfig
import xyz.rthqks.dlog.logic.capture.GetDataFrameFlow
import xyz.rthqks.dlog.logic.capture.OpenDataCapture
import xyz.rthqks.dlog.logic.capture.SendDataFrame
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.state.ChartState

class DataCaptureFeature(
    private val task: Task,
    private val openDataCapture: OpenDataCapture,
    private val dataFrameFlow: GetDataFrameFlow,
    private val sendDataFrame: SendDataFrame,
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

    val legendState = mutableStateOf(Pair(0.0, 0.0))


    fun onWindowClose() {
        stop()
        windowClose()
    }

    fun handle(intent: DataCaptureIntent) {
        when (intent) {
            Connect -> task.fileName?.let {
                val config = WebsocketConfig(it)
                openDataCapture(config)
                coroutineScope.launch(Dispatchers.IO) {
                    while (true) {
                        delay(1000)
                        sendDataFrame(config, """{"c": "d"}""")
                        println("sending")
                    }
                }
                coroutineScope.launch(Dispatchers.IO) {
                    delay(5000)
                    dataFrameFlow(config).collect { frame: Map<String, Double> ->
                        val first = frame["bt"] ?: 0.0
                        val second = frame["et"] ?: 0.0
                        println(frame)
                        legendState.value = Pair(first, second)
                    }
                }
            }

            Capture -> TODO()
        }
    }

    fun onWindowState(windowState: WindowState) {
        println("window: ${windowState.position}, ${windowState.size}, ${windowState.placement}")
    }
}


sealed class DataCaptureIntent : Intent

data object Connect : DataCaptureIntent()
data object Capture : DataCaptureIntent()

