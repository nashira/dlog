package xyz.rthqks.dlog.state

import androidx.compose.runtime.State
import xyz.rthqks.dlog.viewmodel.Reducer

data class AppState(
    val windows: State<List<Reducer<out WindowState>>>,
)