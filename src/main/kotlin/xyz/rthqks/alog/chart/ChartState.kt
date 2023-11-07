package xyz.rthqks.alog.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke

class ChartState(
    val axes: List<Axis>,
    val timeSeries: List<TimeSeries>,
    val annotations: List<Annotation>,
)

data class Axis(
    val ticks: Int,
    val labels: Int,
    val tickSize: Float,
    val bounds: Rect,
    val position: AxisPosition,
    val labelFormatter: (Float) -> String = { it.toString() }
)

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

data class Annotation(
    val text: String,
    val pos: Offset,
    val bounds: Rect,
)

enum class AxisPosition {
    Top, Bottom, Right, Left
}

enum class LineStyle {
    Linear, Cubic
}
