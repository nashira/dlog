package xyz.rthqks.alog.state

import androidx.compose.runtime.State

data class AppState(
    val windows: State<List<WindowState>>,
)