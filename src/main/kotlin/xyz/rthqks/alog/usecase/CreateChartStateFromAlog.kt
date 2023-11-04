package xyz.rthqks.alog.usecase

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import xyz.rthqks.alog.model.AlogDocument
import xyz.rthqks.alog.state.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CreateChartStateFromAlog {

    operator fun invoke(doc: AlogDocument): ChartState {
        val ts = mutableListOf<TimeSeries>()
        val axes = mutableListOf<Axis>()

        if (doc.timex.size > 1) {
            val bts = doc.timex.mapIndexed { idx, d ->
                Offset(d.toFloat(), doc.temp2[idx].toFloat())
            }

            val ets = doc.timex.mapIndexed { idx, d ->
                Offset(d.toFloat(), doc.temp1[idx].toFloat())
            }

            val bounds = Rect(
                doc.xMin.toFloat(),
                doc.yMax.toFloat(),
                doc.xMax.toFloat(),
                doc.yMin.toFloat()
            )

            val dxBounds = Rect(
                doc.xMin.toFloat(),
                doc.zMax.toFloat(),
                doc.xMax.toFloat(),
                doc.zMin.toFloat()
            )

            val fgStroke = Stroke(4f, join = StrokeJoin.Round)
            ts += TimeSeries(dxBounds, bts.derivative(6), Color(0xff8888ff))
            ts += TimeSeries(dxBounds, ets.derivative(6), Color(0xffff8888))
            ts += TimeSeries(bounds, bts, Color.Blue, fgStroke)
            ts += TimeSeries(bounds, ets, Color.Red, fgStroke)

            axes += Axis(25, 5, 5f, bounds, AxisPosition.Left) { "%.0f".format(it) }
            axes += Axis(25, 5, 5f, bounds, AxisPosition.Bottom) {
                "%.0f:%02.0f".format(it / 60, abs(it % 60))
            }
            axes += Axis(25, 5, 5f, dxBounds, AxisPosition.Right) { "%.0f".format(it) }
        }

        return ChartState(doc.title, axes, ts)
    }
}

private fun List<Offset>.derivative(dx: Int = 1): List<Offset> = mapIndexed { index, offset ->
    val prev = get(max(index - dx, 0))
    val next = get(min(index + dx, lastIndex))
    val dxMinutes = (next.x - prev.x) / 60f
    Offset(offset.x, (next.y - prev.y) / dxMinutes)
}
