package xyz.rthqks.dlog.logic

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import xyz.rthqks.dlog.derivative
import xyz.rthqks.dlog.io.AlogDocument
import xyz.rthqks.dlog.state.*
import xyz.rthqks.dlog.state.Annotation
import xyz.rthqks.dlog.toMinSec

class CreateChartStateFromAlog {

    operator fun invoke(doc: AlogDocument): ChartState {
        val ts = mutableListOf<TimeSeries>()
        val axes = mutableListOf<Axis>()
        val annotations = mutableListOf<Annotation>()

        if (doc.timex.size > 1) {
            val bts = doc.timex.mapIndexed { idx, d ->
                Offset(d.toFloat() - doc.chargeTime.toFloat(), doc.temp2[idx].toFloat())
            }

            val ets = doc.timex.mapIndexed { idx, d ->
                Offset(d.toFloat() - doc.chargeTime.toFloat(), doc.temp1[idx].toFloat())
            }

            val xMin = bts.first().x - 30//doc.xMin.toFloat()
            val xMax = bts.last().x + 30//doc.xMax.toFloat()
            val yMin = doc.yMin.toFloat()
            val yMax = doc.yMax.toFloat()
            val zMin = doc.zMin.toFloat()
            val zMax = doc.zMax.toFloat()

            val bounds = Rect(xMin, yMax, xMax, yMin)
            val dxBounds = Rect(xMin, zMax, xMax, zMin)

            val eventsByType = doc.specialEvents.mapIndexed { index, i ->
                val time = doc.timex[i] - doc.chargeTime
                val value = (doc.specialEventValues[index] - 1) * 10
                val type = doc.specialEventTypes[index]
                Triple(type, time.toFloat(), value.toFloat())
            }.groupBy { it.first }
            val fgStroke = Stroke(4f, join = StrokeJoin.Round)

            val air = getPathForEvents(eventsByType, bts, AlogDocument.EVENT_AIR)
            val burner = getPathForEvents(eventsByType, bts, AlogDocument.EVENT_BURNER)

            ts += TimeSeries(dxBounds, ets.derivative(listOf(0f,0f,0f,0f,0f,1f)), Color(0xffff8888))
            ts += TimeSeries(dxBounds, bts.derivative(listOf(0f,0f,0f,0f,0f,1f)), Color(0xff8888ff))
            ts += TimeSeries(bounds, air, Color(0xff88ffff), fgStroke)
            ts += TimeSeries(bounds, burner, Color(0xffff88ff), fgStroke)
            ts += TimeSeries(bounds, ets, Color.Red, fgStroke)
            ts += TimeSeries(bounds, bts, Color.Blue, fgStroke)

            axes += Axis(
                25,
                5,
                5f,
                bounds,
                AxisPosition.Left
            ) { "%.0f".format(it) }
            axes += Axis(
                25,
                5,
                5f,
                bounds,
                AxisPosition.Bottom
            ) {
                it.toMinSec()
            }
            axes += Axis(
                25,
                5,
                5f,
                dxBounds,
                AxisPosition.Right
            ) { "%.0f".format(it) }

            annotations += doc.events.filterIndexed { i, e -> (i == 0 && e.index >= 0) || e.index > 0 }
                .map {
                    val pos = bts[it.index]
                    val text = "${it.name}\n${"%.1f".format(pos.y)}\n${pos.x.toMinSec()}"
                    Annotation(text, pos, bounds)
                }
        }

        return ChartState(axes, ts, annotations)
    }

    private fun getPathForEvents(
        eventsByType: Map<Int, List<Triple<Int, Float, Float>>>,
        bts: List<Offset>,
        type: Int,
    ): MutableList<Offset> {
        val burner = mutableListOf<Offset>()
        eventsByType[type]?.let {
            burner += Offset(0f, it[0].third)
            it.windowed(2, partialWindows = true).forEach { w ->
                if (w.size == 1) {
                    burner += Offset(w[0].second, w[0].third)
                } else {
                    burner += Offset(w[1].second, w[0].third)
                    burner += Offset(w[1].second, w[1].third)
                }
            }
            burner += Offset(bts.last().x, it.last().third)
        }
        return burner
    }
}