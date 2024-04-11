package xyz.rthqks.dlog.io

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import xyz.rthqks.dlog.io.websocket.WebsocketClient
import xyz.rthqks.dlog.logic.capture.GetDataFrameFlow

class DataCaptureService : KoinComponent {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val clients = mutableMapOf<DataCaptureConfig, DataCaptureClient>()
    private val jobs = mutableMapOf<DataCaptureConfig, Job>()

    fun open(config: DataCaptureConfig) {
        println("DataCaptureService open: $config")
        if (jobs[config]?.isActive == true) error("client already open")

        jobs[config] = scope.launch {
            client(config).open()
        }
        println("DataCaptureService opened: $config")
    }

    fun close(config: DataCaptureConfig) {
        jobs[config]?.cancel()
    }

    fun send(config: DataCaptureConfig, value: String) = scope.launch(Dispatchers.IO) {
        client(config).send(value)
    }

    fun receiveFlow(config: DataCaptureConfig): SharedFlow<Map<String, Double>> = client(config).receive

    private fun client(config: DataCaptureConfig): DataCaptureClient = clients.getOrPut(config) {
        when (config) {
            is AlogReplayConfig -> get<AlogReplayClient> {
                parametersOf(config)
            }

            is WebsocketConfig -> get<WebsocketClient> {
                parametersOf(config)
            }
        }
    }
}

sealed class DataCaptureConfig

data class AlogReplayConfig(
    val fileName: String
) : DataCaptureConfig()

data class WebsocketConfig(
    val url: String
) : DataCaptureConfig()

interface DataCaptureClient {
    val receive: SharedFlow<Map<String, Double>>
    suspend fun open()
    suspend fun send(value: String)
}
