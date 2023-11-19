package xyz.rthqks.dlog.ui.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import xyz.rthqks.dlog.viewmodel.ChartViewModel
import xyz.rthqks.dlog.viewmodel.TaskIntent

@Composable
fun ChartWindow(
    vm: ChartViewModel
) {
    val title by vm.titleState
    val chart by vm.chartState
    val handler = vm::handle

    Window(
        title = title,
        onCloseRequest = { handler(TaskIntent.CloseWindow) }
    ) {
//        GlobalMenu(reducer) {
//            Menu("Chart") {
//                Item("Some Item", onClick = reducer.bind(ShowEditSettings))
//            }
//        }

        Chart(chart)
    }
}
