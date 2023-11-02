package xyz.rthqks.alog

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.sqrt


operator fun Offset.times(offset: Offset): Offset = Offset(x * offset.x, y * offset.y)
operator fun Offset.div(offset: Offset): Offset = Offset(x / offset.x, y / offset.y)
operator fun Size.div(size: Size): Offset = Offset(width / size.width, height / size.height)

fun Offset.normalize(): Offset = this / length()
fun Offset.length(): Float = sqrt(x * x + y * y)
