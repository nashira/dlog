package xyz.rthqks.dlog.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import xyz.rthqks.dlog.intent.ShowEditSettings
import xyz.rthqks.dlog.intent.FindFileToOpen
import xyz.rthqks.dlog.intent.FindFileToReplay
import xyz.rthqks.dlog.viewmodel.Reducer

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