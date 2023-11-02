package xyz.rthqks.alog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import xyz.rthqks.alog.intent.CloseWindow
import xyz.rthqks.alog.intent.SelectFiles
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.state.FilePickerWindowState
import java.awt.FileDialog
import java.awt.Frame

@Composable
fun FilePickerWindow(
    state: FilePickerWindowState,
    reducer: Reducer
) = AwtWindow<FileDialog>(
    create = {
        object : FileDialog(null as Frame?, "Choose a file", LOAD) {
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