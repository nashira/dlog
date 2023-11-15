package xyz.rthqks.dlog.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import xyz.rthqks.dlog.state.FindFileWindowState
import xyz.rthqks.dlog.intent.CloseWindow
import xyz.rthqks.dlog.viewmodel.Reducer
import xyz.rthqks.dlog.intent.SelectFiles
import java.awt.FileDialog
import java.awt.Frame

@Composable
fun FilePickerWindow(
    reducer: Reducer<FindFileWindowState>
) = AwtWindow<FileDialog>(
    create = {
        val state = reducer.state
        object : FileDialog(null as Frame?, state.title.value, LOAD) {

            init {
                isMultipleMode = true
            }

            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    reducer(CloseWindow(state))
                    reducer(SelectFiles(files.toList()))
                }
            }
        }
    },
    dispose = FileDialog::dispose
)