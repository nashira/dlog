package xyz.rthqks.alog.usecase

import kotlin.random.Random

class GetRandomTimeSeries {

    operator fun invoke(): List<Pair<Int, Float>> {
        var ran = 0f
        return List(1000) {
            ran += (Random.nextFloat() * 2) - 1
            Pair((it - 10) * 250, ran)
        }
    }
}