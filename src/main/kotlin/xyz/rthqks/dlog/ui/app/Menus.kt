package xyz.rthqks.dlog.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuBarScope
import androidx.compose.ui.window.MenuScope
import xyz.rthqks.dlog.viewmodel.app.Menu

@Composable
fun FrameWindowScope.GlobalMenu(
    handler: (Menu) -> Unit = {},
    content: @Composable MenuBarScope.() -> Unit = {}
) {
    MenuBar {
        Menu(text = "File") {
            Item(
                "Open",
                onClick = { handler(Menu.FileOpen) }
            )
            Item(
                "Replay",
                onClick = { handler(Menu.FileReplay) }
            )
        }
        Menu(text = "Settings") {
            Item(
                "Edit",
                onClick = { handler(Menu.SettingsEdit) }
            )
        }
        content()
    }
}

@Composable
fun MenuScope.TrayMenu(
    handler: (Menu) -> Unit = {},
    content: MenuScope.() -> Unit = {}
) {

    Menu(text = "File") {
        Item(
            "Open",
            onClick = { handler(Menu.FileOpen) }
        )
        Item(
            "Replay",
            onClick = { handler(Menu.FileReplay) }
        )
    }
    Menu(text = "Settings") {
        Item(
            "Edit",
            onClick = { handler(Menu.SettingsEdit) }
        )
    }
    content()
}
