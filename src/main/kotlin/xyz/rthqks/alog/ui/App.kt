@file:Suppress("FunctionName")

package xyz.rthqks.alog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.rememberDialogState
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.logic.SettingsStateReducer
import xyz.rthqks.alog.state.AppState
import xyz.rthqks.alog.state.ChartWindowState
import xyz.rthqks.alog.state.FilePickerWindowState
import xyz.rthqks.alog.state.SettingsWindowState
import xyz.rthqks.alog.ui.settings.SettingsWindow

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
            is SettingsWindowState -> {
                SettingsWindow(window, reducer.get { parametersOf(reducer) })
            }
        }
    }
}
