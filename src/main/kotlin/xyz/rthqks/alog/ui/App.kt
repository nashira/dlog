@file:Suppress("FunctionName")

package xyz.rthqks.alog.ui

import androidx.compose.runtime.Composable
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.state.AppState
import xyz.rthqks.alog.state.ChartWindowState
import xyz.rthqks.alog.state.FilePickerWindowState

@Composable
fun App(
    appState: AppState,
    reducer: Reducer,
) {
    val windows = appState.windows.value


    windows.forEach { window ->
        when (window) {
            is FilePickerWindowState -> FilePickerWindow(window, reducer)
            is ChartWindowState -> ChartWindow(window, reducer)
        }
    }
}
