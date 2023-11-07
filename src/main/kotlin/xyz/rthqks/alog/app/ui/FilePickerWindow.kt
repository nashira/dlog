package xyz.rthqks.alog.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import xyz.rthqks.alog.app.state.FilePickerWindowState
import xyz.rthqks.alog.intent.CloseWindow
import xyz.rthqks.alog.intent.SelectFiles
import xyz.rthqks.alog.logic.Reducer
import java.awt.FileDialog
import java.awt.Frame

@Composable
fun FilePickerWindow(
    reducer: Reducer<FilePickerWindowState>
) = AwtWindow<FileDialog>(
    create = {
        object : FileDialog(null as Frame?, "Choose a file", LOAD) {
            val state = reducer.state

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