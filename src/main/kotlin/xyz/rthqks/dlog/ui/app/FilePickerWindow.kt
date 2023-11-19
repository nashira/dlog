package xyz.rthqks.dlog.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import xyz.rthqks.dlog.viewmodel.app.Menu
import java.awt.FileDialog
import java.awt.Frame

@Composable
fun FilePickerWindow(
    handler: (Menu) -> Unit = {},
) = AwtWindow<FileDialog>(
    create = {

        object : FileDialog(null as Frame?, "", LOAD) {

            init {
                isMultipleMode = true
            }

            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    handler(Menu.SelectFiles(files.toList()))
                }
            }
        }
    },
    dispose = FileDialog::dispose
)