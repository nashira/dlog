package xyz.rthqks.alog.state

sealed class WindowState

data object FilePickerWindowState : WindowState()

data object ChartWindowState : WindowState()

