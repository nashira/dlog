package xyz.rthqks.alog.model

data class Settings(
    val foo: Boolean
)

sealed class Setting

data class BooleanSetting(
    val value: Boolean
) : Setting()