package xyz.rthqks.dlog

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.*
import kotlin.system.measureTimeMillis


operator fun Offset.times(offset: Offset): Offset = Offset(x * offset.x, y * offset.y)
operator fun Offset.div(offset: Offset): Offset = Offset(x / offset.x, y / offset.y)
operator fun Size.div(size: Size): Offset = Offset(width / size.width, height / size.height)

fun List<Offset>.derivative(deltaMinus: Int = 1, deltaPlus: Int = deltaMinus): List<Offset> = mapIndexed { index, offset ->
    val prev = get(max(index - deltaMinus, 0))
    val next = get(min(index + deltaPlus, lastIndex))
    val dx = if (next.x - prev.x > 0) next.x - prev.x else 1f
    // deg/Min
    Offset(offset.x, 60f * (next.y - prev.y) / dx)
}

fun Float.toMinSec() = "%.0f:%02.0f".format(floor(this / 60f), abs(this % 60f))

fun Offset.normalize(): Offset = this / length()
fun Offset.length(): Float = sqrt(x * x + y * y)

fun <R> timeIt(label: String, block: () -> R): R {
    val r: R
    val time = measureTimeMillis { r = block() }
    println("$label: ${time}ms")
    return r
}
