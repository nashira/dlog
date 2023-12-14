package xyz.rthqks.dlog.ui.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import xyz.rthqks.dlog.ui.app.GlobalMenu
import xyz.rthqks.dlog.feature.ChartReplayFeature
import xyz.rthqks.dlog.feature.DataCaptureFeature
import xyz.rthqks.dlog.feature.OpenDataCapture
import java.awt.Toolkit

@Composable
fun DataCaptureWindow(
    vm: DataCaptureFeature,
) {
    val title by vm.titleState
    val chart by vm.chartState

    val screenSize = Toolkit.getDefaultToolkit().screenSize

    Window(
        title = title,
        state = rememberWindowState(),
        onCloseRequest = vm::onWindowClose,
    ) {
        GlobalMenu()

        Column {
            Row {
                Button({vm.handle(OpenDataCapture)}) {
                    Text("Open")
                }
            }
            Row {
                Chart(chart)
            }
        }
    }
}
