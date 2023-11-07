package xyz.rthqks.alog.chart

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import xyz.rthqks.alog.intent.CloseWindow
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.app.state.ChartWindowState
import xyz.rthqks.alog.app.ui.GlobalMenu

@Composable
fun ChartWindow(
    reducer: Reducer<ChartWindowState>
) {
    val state = reducer.state

    Window(
        title = state.title,
        onCloseRequest = { reducer(CloseWindow(state)) }
    ) {

        GlobalMenu(reducer)

        Chart(state.chartState)
    }
}
