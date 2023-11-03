package xyz.rthqks.alog.model

data class AlogDocument(
    val title: String,
    val timeStamps: List<Double>,
    val temp1: List<Double>,
    val temp2: List<Double>,
)