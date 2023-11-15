@file:Suppress("FunctionName")

package xyz.rthqks.dlog.ui.app

import androidx.compose.runtime.Composable
import xyz.rthqks.dlog.viewmodel.FilePickerWindowReducer
import xyz.rthqks.dlog.state.AppState
import xyz.rthqks.dlog.ui.chart.ChartWindow
import xyz.rthqks.dlog.viewmodel.ChartWindowReducer
import xyz.rthqks.dlog.viewmodel.Reducer
import xyz.rthqks.dlog.ui.settings.SettingsWindow
import xyz.rthqks.dlog.viewmodel.SettingsWindowReducer

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
