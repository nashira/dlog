package xyz.rthqks.dlog.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBarScope
import androidx.compose.ui.window.Window
import xyz.rthqks.dlog.viewmodel.TaskViewModel

@Composable
fun TaskWindow(
    viewModel: TaskViewModel,
    menuContent: @Composable MenuBarScope.() -> Unit = {},
    windowContent: @Composable FrameWindowScope.() -> Unit = {},
) {
    val title by viewModel.titleState

    Window(
        title = title,
        onCloseRequest = { }
    ) {
//        GlobalMenu(reducer, menuContent)

        windowContent()
    }
}