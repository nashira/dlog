package xyz.rthqks.alog.state

import xyz.rthqks.alog.model.Setting

sealed class WindowState

data object FilePickerWindowState : WindowState()


data class ChartWindowState(
    val chartState: ChartState,
) : WindowState()

data class SettingsWindowState(
    val title: String,
    val setting: Setting,
) : WindowState()
