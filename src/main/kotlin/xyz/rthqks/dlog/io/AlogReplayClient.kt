package xyz.rthqks.dlog.io

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import xyz.rthqks.dlog.logic.CreateAlogFromMap
import xyz.rthqks.dlog.logic.GetFileContent
import xyz.rthqks.dlog.logic.ParsePythonLiteral
import xyz.rthqks.dlog.toMinSec
import java.io.File

class AlogReplayClient(
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
    private val createAlogFromMap: CreateAlogFromMap,
//    private val getAlogToDataCaptureSettings: GetAlogToDataCaptureSettings,
) : DataCaptureClient {
    override val receive: SharedFlow<Map<String, Double>>
        get() = TODO("Not yet implemented")

    override suspend fun open() {

    }

    override suspend fun send(value: String) {
        TODO("Not yet implemented")
    }

    operator fun invoke(fileName: String) = flow {
        val file = File(fileName)
        val content = getFileContent(file)
        val pythonLiteral = parsePythonLiteral(content)
        val alog = createAlogFromMap(file.name, pythonLiteral) ?: return@flow

        val events = alog.specialEvents.mapIndexed { index, i ->
            val value = (alog.specialEventValues[index] - 1) * 10
            val type = alog.specialEventTypes[index]
            Triple(i, value, type)
        }.toMutableList()

        val ts = mutableMapOf<String, MutableList<DataPoint>>()
        val ns = mutableListOf<Annotation>()
        val start = alog.timex.firstOrNull() ?: 0.0
        val startOff = Clock.System.now().toEpochMilliseconds()

        ns += alog.events.filterIndexed { i, e -> (i == 0 && e.index >= 0) || e.index > 0 }
            .map {
                val time = alog.timex[it.index]
                val temp = alog.temp2[it.index]
                val text = "${it.name}\n${"%.1f".format(temp)}\n${time.toMinSec()}"
                Annotation(time, text)
            }

        alog.timex.forEachIndexed { index, d ->
            val elapsed = Clock.System.now().toEpochMilliseconds() - startOff
            delay(((d - start) * 10).toLong() - elapsed)

            ts.getOrPut("bt") { mutableListOf() } += DataPoint(d, alog.temp2[index])
            ts.getOrPut("et") { mutableListOf() } += DataPoint(d, alog.temp1[index])

            while (events.firstOrNull()?.first == index) {
                val event = events.removeAt(0)
                ts.getOrPut("event-${event.third}") { mutableListOf() } += DataPoint(d, event.second)
            }

            if (index > 0) emit(DataCapture(ts, ns))
        }
//        emit(DataCapture(ts, ns))
    }
}
