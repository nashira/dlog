package xyz.rthqks.alog.app.state

import kotlinx.coroutines.flow.StateFlow
import xyz.rthqks.alog.chart.ChartState
import xyz.rthqks.alog.settings.Setting

sealed class WindowState {
    abstract val title: String
}

data object FilePickerWindowState : WindowState() {
    override val title: String = "Select A File"
}

data class ChartWindowState(
    override val title: String,
    val chartState: ChartState,
) : WindowState()

data class SettingsWindowState(
    val setting: StateFlow<Setting>,
) : WindowState() {
    override val title: String = "Settings"
}
