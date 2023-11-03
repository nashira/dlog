package xyz.rthqks.alog.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color

class ChartState(
    val title: String,
    val axes: List<Axis>,
    val timeSeries: List<TimeSeries>
)

data class Axis(
    val ticks: Int,
    val tickSize: Float,
    val bounds: Rect,
    val position: AxisPosition
)

enum class AxisPosition {
    Top, Bottom, Right, Left
}

data class TimeSeries(
    val bounds: Rect,
    val data: List<Offset>,
    val color: Color,
)
