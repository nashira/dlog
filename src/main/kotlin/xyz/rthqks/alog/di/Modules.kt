package xyz.rthqks.alog.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.rthqks.alog.app.AppStateReducer
import xyz.rthqks.alog.app.FilePickerWindowReducer
import xyz.rthqks.alog.chart.ChartWindowReducer
import xyz.rthqks.alog.settings.SettingsRepo
import xyz.rthqks.alog.settings.SettingsWindowReducer
import xyz.rthqks.alog.usecase.*
import kotlin.random.Random

fun appModule() = module {
    singleOf(::CreateChartStateFromAlog)
    singleOf(::CreateAlogFromMap)
    singleOf(::GetFileContent)
    singleOf(::ParsePythonLiteral)
    singleOf(::GetSettings)
    singleOf(::SetSetting)
    singleOf(::AppStateReducer)
    singleOf(::SettingsRepo)

    scope<AppStateReducer> {
        factoryOf(::SettingsWindowReducer)
        factoryOf(::ChartWindowReducer)
        factoryOf(::FilePickerWindowReducer)
    }

    scope<SettingsWindowReducer> {
        factory { Random.nextInt() }
    }
}
