package xyz.rthqks.dlog.ui.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import xyz.rthqks.dlog.viewmodel.ChartViewModel
import java.awt.Toolkit

@Composable
fun ChartWindow(
    vm: ChartViewModel,
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
//        GlobalMenu(reducer) {
//            Menu("Chart") {
//                Item("Some Item", onClick = reducer.bind(ShowEditSettings))
//            }
//        }

        Chart(chart)
    }
}
