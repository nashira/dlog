package xyz.rthqks.alog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import xyz.rthqks.alog.intent.CloseWindow
import xyz.rthqks.alog.intent.ShowFileSelector
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.state.ChartWindowState

@Composable
fun ChartWindow(
    state: ChartWindowState,
    reducer: Reducer
) {
    Window(
        title = state.chartState.title,
        onCloseRequest = { reducer(CloseWindow(state)) }
    ) {
        MenuBar {
            Menu(
                text = "File"
            ) {
                Item(
                    "Open",
                    onClick = reducer.bind(ShowFileSelector)
                )
            }
        }

        Chart(state.chartState)
    }
}
