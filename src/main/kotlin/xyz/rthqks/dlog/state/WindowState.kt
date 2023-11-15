package xyz.rthqks.dlog.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.StateFlow

sealed class WindowState {
    abstract val title: State<String>
}

data object FindFileWindowState : WindowState() {
    override val title = mutableStateOf("Select a File to Open")
}

data class ChartWindowState(
    override val title: State<String>,
    val chartState: State<ChartState>,
    val clockState: State<String>,
) : WindowState()

data class SettingsWindowState(
    val settings: StateFlow<SettingsState>,
) : WindowState() {
    override val title = mutableStateOf("Settings")
}
