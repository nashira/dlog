package xyz.rthqks.dlog

import androidx.compose.ui.window.application
import org.koin.core.context.GlobalContext.startKoin
import xyz.rthqks.dlog.viewmodel.AppStateReducer
import xyz.rthqks.dlog.ui.app.App
import xyz.rthqks.dlog.di.appModule

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
