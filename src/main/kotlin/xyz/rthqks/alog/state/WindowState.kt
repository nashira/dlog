package xyz.rthqks.alog.state

sealed class WindowState

data object FilePickerWindowState : WindowState()

data class ChartWindowState(
    val chartState: ChartState,
) : WindowState()

