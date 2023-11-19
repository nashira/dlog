package xyz.rthqks.dlog

import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application
import org.koin.core.context.GlobalContext.startKoin
import xyz.rthqks.dlog.di.appModule
import xyz.rthqks.dlog.di.dbModule
import xyz.rthqks.dlog.di.logicModule
import xyz.rthqks.dlog.ui.app.FilePickerWindow
import xyz.rthqks.dlog.ui.app.TrayMenu
import xyz.rthqks.dlog.ui.app.WindowManager
import xyz.rthqks.dlog.viewmodel.app.MenuViewModel

fun main() {
    val koin = startKoin {
        modules(
            appModule(),
            dbModule(),
            logicModule()
        )
    }.koin

    application {
        Tray(
            icon = ColorPainter(Color.LightGray)
        ) {
            val vm: MenuViewModel = koin.get()
            val showPicker by vm.filePicker
            TrayMenu(vm::handle)
            if (showPicker) {
                FilePickerWindow(vm::handle)
            }
        }

//        App(reducer)

        WindowManager(koin.get())
    }
}
