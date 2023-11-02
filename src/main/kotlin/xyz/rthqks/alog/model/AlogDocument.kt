package xyz.rthqks.alog.model

data class AlogDocument(
    val title: String,
    val timeStamps: List<Float>,
    val temp1: List<Float>,
    val temp2: List<Float>,
)