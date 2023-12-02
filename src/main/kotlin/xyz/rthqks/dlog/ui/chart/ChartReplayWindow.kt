package xyz.rthqks.dlog.ui.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import xyz.rthqks.dlog.ui.app.GlobalMenu
import xyz.rthqks.dlog.viewmodel.ChartReplayViewModel
import java.awt.Toolkit

@Composable
fun ChartReplayWindow(
    vm: ChartReplayViewModel,
) {
    val title by vm.titleState
    val chart by vm.replayState.collectAsState()

    val screenSize = Toolkit.getDefaultToolkit().screenSize

    Window(
        title = title,
        state = rememberWindowState(
            width = screenSize.width.dp,
            height = screenSize.height.dp,
        ),
        onCloseRequest = vm::onWindowClose,
    ) {
        GlobalMenu() {
            Menu("Settings") {
                Item("Some Item", onClick = vm::onSettingsOpen)
            }
        }

        Chart(chart)
    }
}
