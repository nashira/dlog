package xyz.rthqks.alog.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import xyz.rthqks.alog.app.state.ChartWindowState
import xyz.rthqks.alog.app.ui.GlobalMenu
import xyz.rthqks.alog.logic.CloseWindow
import xyz.rthqks.alog.logic.Reducer

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
