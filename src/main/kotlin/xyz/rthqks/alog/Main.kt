package xyz.rthqks.alog

import androidx.compose.ui.window.application
import org.koin.core.context.GlobalContext.startKoin
import xyz.rthqks.alog.di.appModule
import xyz.rthqks.alog.app.AppStateReducer
import xyz.rthqks.alog.app.ui.App

fun main() {
    val koin = startKoin {
        modules(appModule())
    }.koin

    val reducer = koin.get<AppStateReducer>()

    application {
        reducer.onApplicationExit {
            exitApplication()
        }

//        Tray(
//            icon = ColorPainter(Color.Red)
//        ) { }

        App(reducer)
    }
}
