package xyz.rthqks.dlog.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import xyz.rthqks.dlog.viewmodel.SettingsViewModel
import xyz.rthqks.dlog.viewmodel.TaskIntent

@Composable
fun SettingsWindow(
    vm: SettingsViewModel
) {
    val title by vm.titleState

    Window(
        title = title,
        onCloseRequest = { vm.handle(TaskIntent.CloseWindow) }
    ) {
//        val settings by state.settings.collectAsState()
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

