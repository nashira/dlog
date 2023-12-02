package xyz.rthqks.dlog.logic

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import xyz.rthqks.dlog.derivative
import xyz.rthqks.dlog.io.DataCapture
import xyz.rthqks.dlog.io.DataPoint
import xyz.rthqks.dlog.state.*
import xyz.rthqks.dlog.state.Annotation
import xyz.rthqks.dlog.toMinSec

class CreateChartStateFromCapture {

    operator fun invoke(data: DataCapture): ChartState {
        val ts = mutableListOf<TimeSeries>()
        val axes = mutableListOf<Axis>()
        val annotations = mutableListOf<Annotation>()

        val config = Config(
            mapOf(
                "bt" to Color.Blue,
                "et" to Color.Red,
                "event-0" to Color(0xff88ffff),
                "event-3" to Color(0xffff88ff),
            )
        )
        val startX = data.timeSeries["bt"]?.firstOrNull()?.time?.toFloat() ?: 0f
        val endX = data.timeSeries["bt"]?.lastOrNull()?.time?.toFloat() ?: 0f

        val bounds = data.timeSeries["bt"]?.getBounds() ?: Rect(0f, 1f, 1f, 0f)
        val dxBounds = Rect(bounds.left, 25f, bounds.right, 0f)
        data.timeSeries.forEach { (t, u) ->
            val offsets = u.map { Offset(it.time.toFloat(), it.value.toFloat()) }
            if (t in setOf("bt", "et")) {
                ts += TimeSeries(
                    bounds,
                    offsets,
                    config.colors[t] ?: Color.Black,
                )
                ts += TimeSeries(
                    dxBounds,
                    offsets.derivative(listOf(1f, 2f, 4f)),
                    config.colors[t] ?: Color.Black,
                )
            } else if (t in setOf("event-0", "event-3")) {
                ts += TimeSeries(
                    bounds,
                    offsets.manhattan(startX, endX),
                    config.colors[t] ?: Color.Black,
                )
            }
        }

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

        annotations += data.annotations.map {
            Annotation(it.note, Offset(it.time.toFloat(), 0f), bounds)
        }
        return ChartState(axes, ts, annotations)
    }
}

data class Config(
    val colors: Map<String, Color>
)


private fun List<Offset>.manhattan(startX: Float, endX: Float): List<Offset> {
    val list = mutableListOf<Offset>()
    if (isEmpty()) return emptyList()

    list += Offset(startX, first().y)
    windowed(2, partialWindows = true).forEach { w ->
        if (w.size == 1) {
            list += Offset(w[0].x, w[0].y)
        } else {
            list += Offset(w[1].x, w[0].y)
            list += Offset(w[1].x, w[1].y)
        }
    }
    list += Offset(endX, last().y)
    return list
}

private fun List<DataPoint>.getBounds(): Rect {
    var xMin = Double.MAX_VALUE
    var xMax = Double.MIN_VALUE
    forEach {
        if (it.time < xMin) xMin = it.time
        if (it.time > xMax) xMax = it.time
    }
    if (xMax - xMin < 480) xMax = xMin + 480
//    var yMin = Double.MAX_VALUE
//    var yMax = Double.MIN_VALUE
    return Rect(xMin.toFloat() - 30f, 250f, xMax.toFloat() + 30f, 0f)
}