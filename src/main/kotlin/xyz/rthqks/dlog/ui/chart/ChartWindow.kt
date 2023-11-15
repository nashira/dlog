package xyz.rthqks.dlog.ui.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import xyz.rthqks.dlog.state.ChartWindowState
import xyz.rthqks.dlog.ui.app.GlobalMenu
import xyz.rthqks.dlog.intent.CloseWindow
import xyz.rthqks.dlog.viewmodel.Reducer

@Composable
fun ChartWindow(
    reducer: Reducer<ChartWindowState>
) {
    val state = reducer.state
    val title by state.title
    val chartState by state.chartState

    Window(
        title = title,
        onCloseRequest = reducer.bind(CloseWindow(state))
    ) {

        GlobalMenu(reducer)

        Chart(chartState, state.clockState)
    }
}
