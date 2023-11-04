package xyz.rthqks.alog.model

data class AlogDocument(
    val title: String,
    val xMin: Double,
    val xMax: Double,
    val yMin: Double,
    val yMax: Double,
    val zMin: Double,
    val zMax: Double,
    val timex: List<Double>,
    val temp1: List<Double>,
    val temp2: List<Double>,
    val timeIndex: List<Int>,
) {

    val chargeIndex: Int
        get() = timeIndex.getOrNull(INDEX_CHARGE) ?: -1

    val chargeTime: Double
        get() = timex.getOrNull(chargeIndex) ?: 0.0

    companion object {
        private const val INDEX_CHARGE = 0
        private const val INDEX_TP = 1
    }
}
