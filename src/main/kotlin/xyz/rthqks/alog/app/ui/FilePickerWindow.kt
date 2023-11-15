package xyz.rthqks.alog.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import xyz.rthqks.alog.app.state.FindFileWindowState
import xyz.rthqks.alog.logic.CloseWindow
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.logic.SelectFiles
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