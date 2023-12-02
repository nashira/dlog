package xyz.rthqks.dlog

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.*
import kotlin.system.measureTimeMillis


operator fun Offset.times(offset: Offset): Offset = Offset(x * offset.x, y * offset.y)
operator fun Offset.div(offset: Offset): Offset = Offset(x / offset.x, y / offset.y)
operator fun Size.div(size: Size): Offset = Offset(width / size.width, height / size.height)

fun List<Offset>.derivative(coefficients: List<Float> = listOf(1f)): List<Offset> = mapIndexed { index, offset ->
    var sum = 0f
    var den = 0f
    coefficients.forEachIndexed { i, c ->
        val di = i + 1
        val prev = get(max(index - di, 0))
        val next = get(min(index + di, lastIndex))
        val dx = if (next.x - prev.x > 0) next.x - prev.x else 1f
        sum += c * (next.y - prev.y) / dx
        den += c
    }
    Offset(offset.x, 60f * sum / den)
}

//fun List<Offset>.derivative(delta: Int = 1): List<Offset> = mapIndexed { index, offset ->
//    val prev = get(max(index - delta, 0))
//    val next = get(min(index + delta, lastIndex))
//    val dx = if (next.x - prev.x > 0) next.x - prev.x else 1f
//
//    // deg/Min
//    Offset(offset.x, 60f * (next.y - prev.y) / dx)
//}

fun Double.toMinSec() = toFloat().toMinSec()
fun Float.toMinSec() = "%.0f:%02.0f".format(floor(this / 60f), abs(this % 60f))

fun Offset.normalize(): Offset {
    val l = length()
    return if (l == 0f) this else this / l
}
fun Offset.length(): Float = sqrt(x * x + y * y)

fun <R> timeIt(label: String, block: () -> R): R {
    val r: R
    val time = measureTimeMillis { r = block() }
    println("$label: ${time}ms")
    return r
}
