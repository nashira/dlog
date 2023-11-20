package xyz.rthqks.dlog.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.rthqks.dlog.db.Database
import xyz.rthqks.dlog.io.AlogReplayClient
import xyz.rthqks.dlog.logic.*
import xyz.rthqks.dlog.logic.db.createDatabase
import xyz.rthqks.dlog.logic.task.CreateTask
import xyz.rthqks.dlog.logic.task.DeleteTask
import xyz.rthqks.dlog.logic.task.GetTasks
import xyz.rthqks.dlog.repo.SettingsRepo
import xyz.rthqks.dlog.repo.TaskRepo
import xyz.rthqks.dlog.repo.TaskScope
import xyz.rthqks.dlog.viewmodel.ChartViewModel
import xyz.rthqks.dlog.viewmodel.SettingsViewModel
import xyz.rthqks.dlog.viewmodel.app.AppViewModel
import xyz.rthqks.dlog.viewmodel.app.MenuViewModel

fun appModule() = module {
    singleOf(::CreateChartStateFromAlog)
    singleOf(::CreateAlogFromMap)
    singleOf(::GetFileContent)
    singleOf(::ParsePythonLiteral)
    singleOf(::GetSettings)
    singleOf(::SetSetting)
    singleOf(::AlogReplayClient)
    singleOf(::CreateChartStateFromCapture)

    singleOf(::SettingsRepo)
    singleOf(::TaskRepo)

    singleOf(::AppViewModel)
    singleOf(::MenuViewModel)

    factoryOf(::TaskScope)

    scope<TaskScope> {
        scopedOf(::SettingsViewModel)
        scopedOf(::ChartViewModel)
    }
}

fun logicModule() = module {
    singleOf(::GetTasks)
    singleOf(::CreateTask)
    singleOf(::DeleteTask)
}

fun dbModule() = module {
    singleOf(::createDatabase)

    single {
        get<Database>().settingEntQueries
    }
    single {
        get<Database>().taskEntQueries
    }
}
