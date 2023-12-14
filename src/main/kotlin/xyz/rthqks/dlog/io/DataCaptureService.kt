package xyz.rthqks.dlog.io

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import xyz.rthqks.dlog.io.websocket.WebsocketClient

class DataCaptureService : KoinComponent {
    private val scope = CoroutineScope(Job() + Dispatchers.IO)
    private val clients = mutableMapOf<Any, DataCaptureClient>()

    suspend fun open(config: DataCaptureConfig) {
        println("DataCaptureService open: $config")
        scope.launch {
            client(config).open()
        }.join()
        println("DataCaptureService opened: $config")
    }

    fun close() {

    }

    fun start() {

    }

    fun stop() {

    }

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
    suspend fun open()
}
