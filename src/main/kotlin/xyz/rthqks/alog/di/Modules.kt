package xyz.rthqks.alog.di

import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.rthqks.alog.logic.AppStateReducer
import xyz.rthqks.alog.state.WindowState
import xyz.rthqks.alog.usecase.GetFileContent
import xyz.rthqks.alog.usecase.GetRandomTimeSeries
import xyz.rthqks.alog.usecase.ParsePythonLiteral

fun appModule() = module {
    singleOf(::GetRandomTimeSeries)
    singleOf(::GetFileContent)
    singleOf(::ParsePythonLiteral)
    singleOf(::AppStateReducer)

    scope<WindowState> {
        scopedOf(::GetRandomTimeSeries)
    }
}
