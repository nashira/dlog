package xyz.rthqks.dlog.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlinx.coroutines.runBlocking
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.rthqks.dlog.db.Database
import xyz.rthqks.dlog.logic.*
import xyz.rthqks.dlog.viewmodel.AppStateReducer
import xyz.rthqks.dlog.viewmodel.FilePickerWindowReducer
import xyz.rthqks.dlog.viewmodel.ChartWindowReducer
import xyz.rthqks.dlog.viewmodel.SettingsWindowReducer
import xyz.rthqks.dlog.repo.SettingsRepo
import xyz.rthqks.dlog.usecase.*
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
        val fileName = "build/settings.db"
        val file = File(fileName)
        val driver = JdbcSqliteDriver("jdbc:sqlite:$fileName")
        if (!file.exists()) {
            println("creating db file")
            file.createNewFile()
            runBlocking {
                Database.Schema.create(driver).await()
            }
        }
        Database(driver)
    }

    single {
        get<Database>().settingQueries
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
