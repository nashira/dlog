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
            )
        )

        val bounds = data.timeSeries["bt"]?.getBounds() ?: Rect(0f, 1f, 1f, 0f)
        val dxBounds = Rect(bounds.left, 25f, bounds.right, 0f)
        data.timeSeries.forEach { t, u ->
            ts += TimeSeries(
                bounds,
                u.map { Offset(it.time.toFloat(), it.value.toFloat()) },
                config.colors[t] ?: Color.Black,
            )
            ts += TimeSeries(
                dxBounds,
                ts.last().data.derivative(6, 6),
                config.colors[t] ?: Color.Black,
            )
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
//
//            annotations += doc.events.filterIndexed { i, e -> (i == 0 && e.index >= 0) || e.index > 0 }
//                .map {
//                    val pos = bts[it.index]
//                    val text = "${it.name}\n${"%.1f".format(pos.y)}\n${pos.x.toMinSec()}"
//                    Annotation(text, pos, bounds)
//                }
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

data class Config(
    val colors: Map<String, Color>
)

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
    return Rect(xMin.toFloat(), 250f, xMax.toFloat(), 0f)
}