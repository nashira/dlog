package xyz.rthqks.dlog.io

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import xyz.rthqks.dlog.logic.CreateAlogFromMap
import xyz.rthqks.dlog.logic.GetFileContent
import xyz.rthqks.dlog.logic.ParsePythonLiteral
import java.io.File

class AlogReplayClient(
    private val getFileContent: GetFileContent,
    private val parsePythonLiteral: ParsePythonLiteral,
    private val createAlogFromMap: CreateAlogFromMap,
) {

    operator fun invoke(fileName: String) = flow {
        val file = File(fileName)
        val content = getFileContent(file)
        val pythonLiteral = parsePythonLiteral(content)
        val alog = createAlogFromMap(file.name, pythonLiteral) ?: return@flow

        val ts = mutableMapOf<String, MutableList<DataPoint>>()
        val ns = mutableListOf<Annotation>()
        alog.timex.forEachIndexed { index, d ->
//            println("$index $d")
            ts.getOrPut("bt") { mutableListOf() } += DataPoint(d, alog.temp2[index])
            ts.getOrPut("et") { mutableListOf() } += DataPoint(d, alog.temp1[index])
            if (index > 0)
            emit(DataCapture(ts, ns))

            val nextTime = alog.timex.getOrNull(index + 1) ?: 0.0
            delay(((nextTime - d) * 1000).toLong())
        }
    }
}
