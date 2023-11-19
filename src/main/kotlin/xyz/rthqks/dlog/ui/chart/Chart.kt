package xyz.rthqks.dlog.ui.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.rthqks.dlog.div
import xyz.rthqks.dlog.length
import xyz.rthqks.dlog.normalize
import xyz.rthqks.dlog.state.Axis
import xyz.rthqks.dlog.state.AxisPosition
import xyz.rthqks.dlog.state.ChartState
import xyz.rthqks.dlog.state.LineStyle


@Composable
fun Chart(
    state: ChartState,
) {

    Column(Modifier.fillMaxSize()) {
        Row {
            ChartCanvas(state)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ChartCanvas(state: ChartState) {
    val textMeasurer = rememberTextMeasurer()

    val textStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.Gray
    )
    val padding = 72.dp

    Canvas(
        modifier = Modifier
            .background(Color.White)
            .onPointerEvent(PointerEventType.Move) {
            }
            .fillMaxSize()
    ) {
        if (size.width <= padding.value || size.height <= padding.value) return@Canvas

        inset(padding.value) {
            state.axes.forEach {
                drawAxis(it, textMeasurer, textStyle)
            }

            state.timeSeries.forEach {
                val path = when (it.lineStyle) {
                    LineStyle.Linear -> getLinearPath(it.data)
                    LineStyle.Cubic -> getCubicPath(it.data)
                }
                val transform = it.bounds.fitTo(size)
                path.transform(transform)
                drawPath(path, it.color, style = it.style)
            }

            state.annotations.forEach {
                val transform = it.bounds.fitTo(size)
                val result = textMeasurer.measure(it.text, textStyle)
                val pos = transform * Offset(it.pos.x, it.bounds.top)
                val pos2 = transform * Offset(it.pos.x, it.bounds.bottom)

                drawLine(Color.Gray, pos, pos2)

                drawText(
                    textLayoutResult = result,
                    topLeft = Offset(pos.x + 10f, pos.y)
                )
            }
        }
    }
}

private fun DrawScope.drawAxis(axis: Axis, textMeasurer: TextMeasurer, textStyle: TextStyle) {
    val bounds = axis.bounds
    val transform = bounds.fitTo(size)
    val start = when (axis.position) {
        AxisPosition.Top -> bounds.topLeft
        AxisPosition.Right -> bounds.bottomRight
        AxisPosition.Bottom,
        AxisPosition.Left -> bounds.bottomLeft
    }
    val end = when (axis.position) {
        AxisPosition.Top,
        AxisPosition.Right -> bounds.topRight

        AxisPosition.Bottom -> bounds.bottomRight
        AxisPosition.Left -> bounds.topLeft
    }
    val axisVec = (end - start)
    val axisUnit = axisVec.normalize()
    val axisNormal = when (axis.position) {
        AxisPosition.Bottom,
        AxisPosition.Right -> Offset(-axisUnit.y, axisUnit.x)

        AxisPosition.Top,
        AxisPosition.Left -> Offset(axisUnit.y, -axisUnit.x)
    }
    val tickGap = axisVec.length() / axis.ticks

    drawLine(Color.Gray, transform * start, transform * end)

    (0..axis.ticks).forEach {
        val c = transform * (start + axisUnit * (it * tickGap))
        drawLine(
            Color.Gray,
            c + (axisNormal * axis.tickSize),
            c + (axisNormal * -axis.tickSize)
        )
    }

    val labelGap = axisVec.length() / axis.labels
    (0..axis.labels).forEach {
        val pos = start + (axisUnit * (it * labelGap))
        val c = transform * pos
//        val tickVal = pos.length()
        val tickVal = when (axis.position) {
            AxisPosition.Top,
            AxisPosition.Bottom -> pos.x

            AxisPosition.Right,
            AxisPosition.Left -> pos.y
        }

        val text = axis.labelFormatter(tickVal)
        val result = textMeasurer.measure(text, textStyle)

        val p = 10
        val o = when (axis.position) {
            AxisPosition.Top -> Offset(c.x - result.size.width / 2f, c.y - result.size.height - p)
            AxisPosition.Bottom -> Offset(c.x - result.size.width / 2f, c.y + p)
            AxisPosition.Right -> Offset(c.x + p, c.y - result.size.height / 2f)
            AxisPosition.Left -> Offset(c.x - result.size.width - p, c.y - result.size.height / 2f)
        }

        drawText(
            textLayoutResult = result,
            topLeft = o
        )
    }
}

private fun getLinearPath(data: List<Offset>): Path = Path().apply {
    data.forEachIndexed { i, it ->
        if (i == 0) {
            moveTo(it.x, it.y)
        } else {
            lineTo(it.x, it.y)
        }
    }
}

private fun getCubicPath(
    data: List<Offset>,
): Path = Path().apply {
    val tan = (listOf(data.first()) + data + data.last()).windowed(3).map {
        (it[2] - it[0]).normalize()
    }
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

private operator fun Offset.minus(intOffset: IntOffset) = Offset(x - intOffset.x, y - intOffset.y)

private fun Offset.dot(offset: Offset) = offset.x * x + offset.y * y

private operator fun Matrix.times(offset: Offset) = map(offset)

private fun Rect.fitTo(window: Size): Matrix = Matrix().apply {
    val scale = window / size
    translate(-left * scale.x, -top * scale.y)
    scale(scale.x, scale.y)
}
