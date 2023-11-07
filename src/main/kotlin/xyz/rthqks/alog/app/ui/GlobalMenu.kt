package xyz.rthqks.alog.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import xyz.rthqks.alog.intent.ShowEditSettings
import xyz.rthqks.alog.intent.ShowFileSelector
import xyz.rthqks.alog.logic.Reducer

@Composable
fun FrameWindowScope.GlobalMenu(
    reducer: Reducer<*>
) {

    MenuBar {
        Menu(text = "File") {
            Item(
                "Open",
                onClick = reducer.bind(ShowFileSelector)
            )
        }
        Menu(text = "Settings") {
            Item(
                "Edit",
                onClick = reducer.bind(ShowEditSettings)
            )
        }
    }
}