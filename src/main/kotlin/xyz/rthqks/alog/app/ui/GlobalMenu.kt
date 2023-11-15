package xyz.rthqks.alog.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import xyz.rthqks.alog.logic.ShowEditSettings
import xyz.rthqks.alog.logic.FindFileToOpen
import xyz.rthqks.alog.logic.FindFileToReplay
import xyz.rthqks.alog.logic.Reducer

@Composable
fun FrameWindowScope.GlobalMenu(
    reducer: Reducer<*>
) {

    MenuBar {
        Menu(text = "File") {
            Item(
                "Open",
                onClick = reducer.bind(FindFileToOpen)
            )
            Item(
                "Replay",
                onClick = reducer.bind(FindFileToReplay)
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