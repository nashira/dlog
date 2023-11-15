package xyz.rthqks.alog.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.rthqks.alog.Database
import xyz.rthqks.alog.app.AppStateReducer
import xyz.rthqks.alog.app.FilePickerWindowReducer
import xyz.rthqks.alog.chart.ChartWindowReducer
import xyz.rthqks.alog.settings.SettingsWindowReducer
import xyz.rthqks.alog.settings.repo.SettingsRepo
import xyz.rthqks.alog.usecase.*
import java.io.File
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

    single {
        val fileName = "settings.db"
        val driver = JdbcSqliteDriver("jdbc:sqlite:$fileName")

        if (!File(fileName).exists()) {
            Database.Schema.create(driver)
        }

        Database(driver)
    }

    single {
        get<Database>().settingQueries
    }

    single {
        get<Database>().windowQueries
    }


    scope<AppStateReducer> {
        factoryOf(::SettingsWindowReducer)
        factoryOf(::ChartWindowReducer)
        factoryOf(::FilePickerWindowReducer)
    }

    scope<SettingsWindowReducer> {
        factory { Random.nextInt() }
    }
}
