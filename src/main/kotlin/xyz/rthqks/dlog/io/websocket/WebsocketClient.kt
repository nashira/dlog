package xyz.rthqks.dlog.io.websocket

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.util.Identity.decode
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import xyz.rthqks.dlog.io.DataCaptureClient
import xyz.rthqks.dlog.io.WebsocketConfig

class WebsocketClient(
    private val config: WebsocketConfig
) : DataCaptureClient {
    private val client = HttpClient {
        install(WebSockets)
    }
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val sendFlow = MutableSharedFlow<String>(extraBufferCapacity = 10)
    private val _receiveFlow = MutableSharedFlow<Map<String, Double>>(extraBufferCapacity = 100)
    override val receive: SharedFlow<Map<String, Double>> = _receiveFlow.asSharedFlow()

    override suspend fun open() {
        println("ws client $this")
        client.webSocket(urlString = config.url) {
            println("connected")
            sendFlow.onEach {
                println("sendFlow $it")
                send(it)
            }.launchIn(this)

            _receiveFlow.emitAll(
                incoming
                    .consumeAsFlow()
                    .transform {
                        println("ws frame: $it")
                        if (it is Frame.Text) {
                            val text = it.readText()
                            val map = json.decodeFromString<Foo>(text)
                            println(map)
                            emit(map.d)
                        }
                    })
        }
    }

    override suspend fun send(value: String) {
        println("ws client $this")
        sendFlow.emit(value)
        println("ws client emit $this")
    }
}

@Serializable
private data class Foo(
    val d: Map<String, Double>
)
