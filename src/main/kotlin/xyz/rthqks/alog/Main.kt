package xyz.rthqks.alog

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin
import xyz.rthqks.alog.di.appModule
import xyz.rthqks.alog.intent.ExitApplication
import xyz.rthqks.alog.intent.RefreshData
import xyz.rthqks.alog.logic.AppStateReducer
import xyz.rthqks.alog.ui.App
import java.awt.Taskbar

fun main() {
    val koin = startKoin {
        modules(appModule())
    }.koin

    val reducer = koin.get<AppStateReducer>()
    val appState = reducer.appState

//    val coroutineScope = CoroutineScope(Job())

    application {
        reducer.onApplicationExit {
            exitApplication()
        }

        Tray(
            icon = ColorPainter(Color.Red)
        ) {

        }

        App(appState, reducer)
    }
}
