package xyz.rthqks.alog.app.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.StateFlow
import xyz.rthqks.alog.chart.ChartState
import xyz.rthqks.alog.settings.Setting

sealed class WindowState {
    abstract val title: State<String>
}

data object FilePickerWindowState : WindowState() {
    override val title = mutableStateOf("Select A File")
}

data class ChartWindowState(
    override val title: State<String>,
    val chartState: State<ChartState>,
    val clockState: State<String>,
) : WindowState()

data class SettingsWindowState(
    val setting: StateFlow<Setting>,
) : WindowState() {
    override val title = mutableStateOf("Settings")
}
