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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import xyz.rthqks.alog.div
import xyz.rthqks.alog.intent.CloseWindow
import xyz.rthqks.alog.intent.ShowFileSelector
import xyz.rthqks.alog.length
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.normalize
import xyz.rthqks.alog.state.ChartWindowState

@Composable
fun ChartWindow(
    state: ChartWindowState,
    reducer: Reducer
) {
    Window(onCloseRequest = {reducer(CloseWindow(state))}) {
        MenuBar {
            Menu(
                text = "File"
            ) {
                Item(
                    "Open",
                    onClick = reducer.bind(ShowFileSelector)
                )
            }
        }

        Canvas(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            val timeSeries = emptyList<Pair<Int, Float>>()

            inset(20f, 20f) {
                println(size)
                if (timeSeries.isEmpty()) return@Canvas

                val data = timeSeries.map { Offset(it.first.toFloat(), it.second) }
                val path = getLinearPath(data)
//        val path = getCubicPath(data)
                val bounds = path.getBounds()
                val transform = bounds.fitTo(size)
                path.transform(transform)

                val style = Stroke(
                    width = 2f,
                    join = StrokeJoin.Round
                )
                // flip the y-axis
                scale(1f, -1f) {
                    drawLine(
                        Color.Gray,
                        transform.map(Offset(0f, 0f)),
                        transform.map(Offset(0f, bounds.bottom))
                    )
                    drawLine(
                        Color.Gray,
                        transform.map(Offset(0f, 0f)),
                        transform.map(Offset(bounds.right, 0f))
                    )
                    drawPath(path, Color.Blue, style = style)
                }
            }
        }
    }
}


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