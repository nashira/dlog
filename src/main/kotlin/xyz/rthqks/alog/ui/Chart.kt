package xyz.rthqks.alog.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import xyz.rthqks.alog.div
import xyz.rthqks.alog.length
import xyz.rthqks.alog.normalize
import xyz.rthqks.alog.state.Axis
import xyz.rthqks.alog.state.AxisPosition
import xyz.rthqks.alog.state.ChartState


@Composable
fun Chart(
    state: ChartState
) {
    Canvas(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        inset(20f, 20f) {
            println(size)
            val style = Stroke(
                width = 2f,
                join = StrokeJoin.Round
            )

            state.axes.forEach {
                drawAxis(it)
            }

            state.timeSeries.forEach {
                val path = getLinearPath(it.data)
                val transform = it.bounds.fitTo(size)
                path.transform(transform)
                drawPath(path, it.color, style = style)
            }
        }
    }
}

private fun DrawScope.drawAxis(axis: Axis) {
    val bounds = axis.bounds
    val transform = bounds.fitTo(size)
    val start = transform * when (axis.position) {
        AxisPosition.Top -> bounds.topLeft
        AxisPosition.Right -> bounds.bottomRight
        AxisPosition.Bottom,
        AxisPosition.Left -> bounds.bottomLeft
    }
    val end = transform * when (axis.position) {
        AxisPosition.Top,
        AxisPosition.Right -> bounds.topRight

        AxisPosition.Bottom -> bounds.bottomRight
        AxisPosition.Left -> bounds.topLeft
    }
    val tickStart = when (axis.position) {
        AxisPosition.Top,
        AxisPosition.Bottom -> Offset(0f, axis.tickSize)

        AxisPosition.Right,
        AxisPosition.Left -> Offset(axis.tickSize, 0f)
    }
    val tickEnd = when (axis.position) {
        AxisPosition.Top,
        AxisPosition.Bottom -> Offset(0f, -axis.tickSize)

        AxisPosition.Right,
        AxisPosition.Left -> Offset(-axis.tickSize, 0f)
    }
    val tickVec = (end - start)
    val tickVecNorm = tickVec.normalize()
    val tickGap = tickVec.length() / axis.ticks

    drawLine(Color.Gray, start, end)

    (1..axis.ticks).forEach {
        val c = start + tickVecNorm * (it * tickGap)
        drawLine(
            Color.Gray,
            c + tickStart,
            c + tickEnd
        )
    }
}

private operator fun Matrix.times(offset: Offset) = map(offset)

private fun Rect.fitTo(window: Size): Matrix = Matrix().apply {
    val scale = window / size
    translate(-left * scale.x, -top * scale.y)
    scale(scale.x, scale.y)
}

private fun getLinearPath(data: List<Offset>): Path {
    return Path().apply {
        data.forEachIndexed { i, it ->
            if (i == 0) {
                moveTo(it.x, it.y)
            } else {
                lineTo(it.x, it.y)
            }
        }
    }
}

private fun getCubicPath(
    data: List<Offset>,
): Path {
    val tan = (listOf(data.first()) + data + data.last()).windowed(3).map {
        (it[2] - it[0]).normalize()
    }
    return Path().apply {
        (data.first()).let {
            moveTo(it.x, it.y)
        }
        for (i in 1 until data.size) {
            val p1 = data[i - 1]
            val p2 = data[i]
            val t1 = tan[i - 1]
            val t2 = tan[i]
            val l = (p2 - p1).length() / 3

            val c1 = p1 + (t1 * l)
            val c2 = p2 - (t2 * l)
            cubicTo(c1.x, c1.y, c2.x, c2.y, p2.x, p2.y)
        }
    }
}
