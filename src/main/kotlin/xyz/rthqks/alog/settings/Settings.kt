package xyz.rthqks.alog.settings

data class Settings(
    val foo: Boolean
)

sealed class Setting

data class BooleanSetting(
    val value: Boolean
) : Setting()