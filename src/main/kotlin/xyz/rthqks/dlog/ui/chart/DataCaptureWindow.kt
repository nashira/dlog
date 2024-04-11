package xyz.rthqks.dlog.ui.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.flow.*
import xyz.rthqks.dlog.feature.Capture
import xyz.rthqks.dlog.feature.Connect
import xyz.rthqks.dlog.feature.DataCaptureFeature
import xyz.rthqks.dlog.ui.app.GlobalMenu

@Composable
fun DataCaptureWindow(
    feature: DataCaptureFeature,
) {
    val title by feature.titleState
    val chart by feature.chartState
    val legend by feature.legendState

    val windowState = rememberWindowState()

    Window(
        title = title,
        state = windowState,
        onCloseRequest = feature::onWindowClose,
    ) {
//        LaunchedEffect(windowState) {
//            MutableSharedFlow<WindowState>().also { f ->
//                snapshotFlow { windowState.size }.onEach { f.emit(windowState) }.launchIn(this)
//                snapshotFlow { windowState.position }.onEach { f.emit(windowState) }.launchIn(this)
//                snapshotFlow { windowState.placement }.onEach { f.emit(windowState) }.launchIn(this)
//            }
//                .onEach(feature::onWindowState)
//                .launchIn(this)
//        }

        GlobalMenu()

        Column {
            Row(Modifier.padding(horizontal = 10.dp)) {
                Button({ feature.handle(Connect) }) {
                    Text("Connect")
                }
                Spacer(Modifier.width(10.dp))
                Button({ feature.handle(Capture) }) {
                    Text("Capture")
                }
            }
            Box(Modifier.fillMaxSize()) {
                Chart(chart)
                Column(
                    modifier = Modifier
                        .width(120.dp)
                        .height(Dp.Unspecified)
                        .padding(end = 10.dp)
                        .align(Alignment.CenterEnd)
                        .background(Color(0.9f, 0.9f, 0.9f)),
                ) {
                    val modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 10.dp)
                    Text("BT: ${legend.first}", modifier = modifier.padding(top = 10.dp))
                    Text("ET: ${legend.second}", modifier = modifier)
                    Text("∆BT: ", modifier = modifier)
                    Text("∆ET: ", modifier = modifier)
                }
            }
        }
    }
}
