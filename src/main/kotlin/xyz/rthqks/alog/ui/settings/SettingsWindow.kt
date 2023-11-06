package xyz.rthqks.alog.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import xyz.rthqks.alog.intent.ChangeSetting
import xyz.rthqks.alog.intent.CloseWindow
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.state.SettingsWindowState

@Composable
fun SettingsWindow(
    state: SettingsWindowState,
    reducer: Reducer
) {
    Window(
        title = state.title,
        onCloseRequest = { reducer(CloseWindow(state)) }
    ) {
        Column {
            Switch(false, onCheckedChange = { reducer(ChangeSetting(state.setting)) })
        }
    }
}