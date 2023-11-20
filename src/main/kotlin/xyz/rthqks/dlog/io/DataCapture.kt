package xyz.rthqks.dlog.io

data class DataCapture(
    val timeSeries: Map<String, List<DataPoint>>,
    val annotations: List<Annotation>,
)

data class DataPoint(
    val time: Double,
    val value: Double,
)

data class Annotation(
    val time: Double,
    val note: String,
)
