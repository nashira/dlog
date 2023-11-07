package xyz.rthqks.alog.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Window
import xyz.rthqks.alog.app.state.SettingsWindowState
import xyz.rthqks.alog.intent.ChangeSetting
import xyz.rthqks.alog.intent.CloseWindow
import xyz.rthqks.alog.logic.Reducer

@Composable
fun SettingsWindow(
    reducer: Reducer<SettingsWindowState>
) {
    val state = reducer.state

    Window(
        title = state.title,
        onCloseRequest = { reducer(CloseWindow(state)) }
    ) {
        Column {
            val settingState = state.setting.collectAsState()

            when (val setting = settingState.value) {
                is BooleanSetting -> {
                    Switch(setting.value, onCheckedChange = {
                        reducer(ChangeSetting(BooleanSetting(it)))
                    })
                }
            }
        }
    }
}