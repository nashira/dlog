@file:Suppress("FunctionName")

package xyz.rthqks.alog.app.ui

import androidx.compose.runtime.Composable
import xyz.rthqks.alog.app.FilePickerWindowReducer
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
    val reducers = reducer.state.windows.value

    reducers.forEach { windowStateReducer ->
        when (windowStateReducer) {
            is FilePickerWindowReducer -> FilePickerWindow(windowStateReducer)
            is ChartWindowReducer -> ChartWindow(windowStateReducer)
            is SettingsWindowReducer -> {
                SettingsWindow(windowStateReducer)
            }
        }
    }
}
