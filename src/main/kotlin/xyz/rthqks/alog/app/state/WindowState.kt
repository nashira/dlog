package xyz.rthqks.alog.app.state

import androidx.compose.runtime.State
import xyz.rthqks.alog.settings.Setting
import xyz.rthqks.alog.chart.ChartState

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
    val setting: State<Setting>,
) : WindowState() {
    override val title: String = "Settings"
}
