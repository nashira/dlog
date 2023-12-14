package xyz.rthqks.dlog.io.websocket

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import xyz.rthqks.dlog.io.DataCaptureClient
import xyz.rthqks.dlog.io.WebsocketConfig
import java.io.IOException

class WebsocketClient(
    private val config: WebsocketConfig
) : DataCaptureClient {
    private val sendFlow = MutableSharedFlow<String>()
    private var receiveFlow: Flow<String>? = null
    private val client = HttpClient {
        install(WebSockets)
    }

    override suspend fun open() {
        val connected = CompletableDeferred<Unit>()
        receiveFlow = flow {
            try {
                client.webSocket(urlString = config.url) {
                    println("connected")
                    connected.complete(Unit)
                    launch {
                        send("""{"c": "d"}""")
                        sendFlow.collect(::send)
                        this@webSocket.cancel("send flow finished")
                    }
                    emitAll(
                        incoming
                            .receiveAsFlow()
                            .transform {
                                println("ws frame: $it")
                                if (it is Frame.Text) emit(it.readText())
                            })
                }
            } catch (e: Exception) {
                println("error: $e")
                connected.completeExceptionally(e)
            }
        }.flowOn(Dispatchers.IO)

        receiveFlow?.collect {
            println(it)
        }
    }
}

