package xyz.rthqks.alog.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import xyz.rthqks.alog.app.AppStateReducer
import xyz.rthqks.alog.app.state.FilePickerWindowReducer
import xyz.rthqks.alog.chart.ChartWindowReducer
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.settings.SettingsWindowReducer
import xyz.rthqks.alog.settings.SettingsRepo
import xyz.rthqks.alog.usecase.CreateAlogFromMap
import xyz.rthqks.alog.usecase.CreateChartStateFromAlog
import xyz.rthqks.alog.usecase.GetFileContent
import xyz.rthqks.alog.usecase.ParsePythonLiteral

fun appModule() = module {
    singleOf(::CreateChartStateFromAlog)
    singleOf(::CreateAlogFromMap)
    singleOf(::GetFileContent)
    singleOf(::ParsePythonLiteral)
    val appReducer = singleOf(::AppStateReducer)
    singleOf(::SettingsRepo)

    scope<AppStateReducer> {
        appReducer bind Reducer::class
        factoryOf(::SettingsWindowReducer)
        factoryOf(::ChartWindowReducer)
        factoryOf(::FilePickerWindowReducer)
    }
}
