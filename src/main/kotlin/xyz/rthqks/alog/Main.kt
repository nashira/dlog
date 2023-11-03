package xyz.rthqks.alog

import androidx.compose.ui.window.application
import org.koin.core.context.GlobalContext.startKoin
import xyz.rthqks.alog.di.appModule
import xyz.rthqks.alog.logic.AppStateReducer
import xyz.rthqks.alog.ui.App

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

//        Tray(
//            icon = ColorPainter(Color.Red)
//        ) { }

        App(appState, reducer)
    }
}
