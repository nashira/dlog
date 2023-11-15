package xyz.rthqks.dlog.io

data class AlogDocument(
    val fileName: String,
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
    val specialEvents: List<Int>,
    val specialEventTypes: List<Int>,
    val specialEventValues: List<Double>,
    val tpIndex: Int,
) {

    val chargeIndex: Int
        get() = timeIndex.getOrNull(INDEX_CHARGE) ?: -1

    val chargeTime: Double
        get() = timex.getOrNull(chargeIndex) ?: 0.0

    val events by lazy {
        listOf(
            AlogEvent("Charge", timeIndex[INDEX_CHARGE]),
            AlogEvent("Turning Point", tpIndex),
            AlogEvent("Dry End", timeIndex[INDEX_DE]),
            AlogEvent("FC Start", timeIndex[INDEX_FCS]),
            AlogEvent("FC End", timeIndex[INDEX_FCE]),
            AlogEvent("SC Start", timeIndex[INDEX_SCS]),
            AlogEvent("SC End", timeIndex[INDEX_SCE]),
            AlogEvent("Drop", timeIndex[INDEX_DROP]),
        )
    }

    companion object {
        private const val INDEX_CHARGE = 0
        private const val INDEX_DE = 1
        private const val INDEX_FCS = 2
        private const val INDEX_FCE = 3
        private const val INDEX_SCS = 4
        private const val INDEX_SCE = 5
        private const val INDEX_DROP = 6

        const val EVENT_AIR = 0
        const val EVENT_BURNER = 3
    }
}

data class AlogEvent(
    val name: String,
    val index: Int
)
