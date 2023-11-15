package xyz.rthqks.dlog.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import xyz.rthqks.dlog.state.SettingsWindowState
import xyz.rthqks.dlog.intent.CloseWindow
import xyz.rthqks.dlog.viewmodel.Reducer

@Composable
fun SettingsWindow(
    reducer: Reducer<SettingsWindowState>
) {
    val state = reducer.state
    val title by state.title

    Window(
        title = title,
        onCloseRequest = reducer.bind(CloseWindow(state))
    ) {
        val settings by state.settings.collectAsState()
        Column {

//
//            when (val setting = settingState) {
//                is BooleanSetting -> {
//                    Switch(setting.value, onCheckedChange = {
//                        reducer(ChangeSetting(BooleanSetting(it)))
//                    })
//                }
//            }
        }
    }
}

