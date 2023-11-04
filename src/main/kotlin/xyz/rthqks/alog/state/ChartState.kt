package xyz.rthqks.alog.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke

class ChartState(
    val title: String,
    val axes: List<Axis>,
    val timeSeries: List<TimeSeries>
)

data class Axis(
    val ticks: Int,
    val labels: Int,
    val tickSize: Float,
    val bounds: Rect,
    val position: AxisPosition,
    val labelFormatter: (Float) -> String = { it.toString() }
)

enum class AxisPosition {
    Top, Bottom, Right, Left
}

enum class LineStyle {
    Linear, Cubic
}

data class TimeSeries(
    val bounds: Rect,
    val data: List<Offset>,
    val color: Color,
    val style: Stroke = DefaultStroke,
    val lineStyle: LineStyle = LineStyle.Linear
) {
    companion object {
        val DefaultStroke = Stroke(
            width = 2f,
            join = StrokeJoin.Round,
        )
    }
}
