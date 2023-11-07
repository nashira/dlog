package xyz.rthqks.alog.app.state

import androidx.compose.runtime.State
import xyz.rthqks.alog.logic.Reducer

data class AppState(
    val windows: State<List<Reducer<out WindowState>>>,
)