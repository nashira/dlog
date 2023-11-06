package xyz.rthqks.alog.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.rthqks.alog.logic.AppStateReducer
import xyz.rthqks.alog.logic.SettingsStateReducer
import xyz.rthqks.alog.repo.SettingsRepo
import xyz.rthqks.alog.state.ChartWindowState
import xyz.rthqks.alog.state.WindowState
import xyz.rthqks.alog.usecase.CreateAlogFromMap
import xyz.rthqks.alog.usecase.CreateChartStateFromAlog
import xyz.rthqks.alog.usecase.GetFileContent
import xyz.rthqks.alog.usecase.ParsePythonLiteral

fun appModule() = module {
    singleOf(::CreateChartStateFromAlog)
    singleOf(::CreateAlogFromMap)
    singleOf(::GetFileContent)
    singleOf(::ParsePythonLiteral)
    singleOf(::AppStateReducer)
    singleOf(::SettingsRepo)

    scope<AppStateReducer> {
        factoryOf(::SettingsStateReducer)
    }
}
