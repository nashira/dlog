@file:Suppress("FunctionName")

package xyz.rthqks.alog.app.ui

import androidx.compose.runtime.Composable
import xyz.rthqks.alog.app.state.*
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.settings.SettingsWindowReducer
import xyz.rthqks.alog.settings.SettingsWindow
import xyz.rthqks.alog.chart.ChartWindow
import xyz.rthqks.alog.chart.ChartWindowReducer

@Composable
fun App(
    reducer: Reducer<AppState>,
) {
    val windows = reducer.state.windows.value

    windows.forEach { window ->
        when (window) {
            is FilePickerWindowReducer -> FilePickerWindow(window)
            is ChartWindowReducer -> ChartWindow(window)
            is SettingsWindowReducer -> {
                SettingsWindow(window)
            }
        }
    }
}
