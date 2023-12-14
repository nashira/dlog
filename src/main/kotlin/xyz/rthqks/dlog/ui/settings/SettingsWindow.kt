package xyz.rthqks.dlog.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import xyz.rthqks.dlog.feature.SettingsFeature

@Composable
fun SettingsWindow(
    vm: SettingsFeature
) {

    Window(
        title = "Settings",
        onCloseRequest = vm::onWindowClose
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

