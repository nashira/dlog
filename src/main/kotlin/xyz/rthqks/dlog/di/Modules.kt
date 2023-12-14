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
import xyz.rthqks.dlog.feature.ChartReplayFeature
import xyz.rthqks.dlog.feature.ChartFeature
import xyz.rthqks.dlog.feature.DataCaptureFeature
import xyz.rthqks.dlog.feature.SettingsFeature
import xyz.rthqks.dlog.feature.app.AppFeature
import xyz.rthqks.dlog.feature.app.MenuFeature
import xyz.rthqks.dlog.io.DataCaptureService
import xyz.rthqks.dlog.io.websocket.WebsocketClient
import xyz.rthqks.dlog.logic.capture.OpenDataCapture

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
    singleOf(::DataCaptureService)

    singleOf(::AppFeature)
    singleOf(::MenuFeature)

    factoryOf(::TaskScope)
    factoryOf(::WebsocketClient)

    scope<TaskScope> {
        scopedOf(::SettingsFeature)
        scopedOf(::ChartFeature)
        scopedOf(::ChartReplayFeature)
        scopedOf(::DataCaptureFeature)
    }
}

fun logicModule() = module {
    singleOf(::GetTasks)
    singleOf(::CreateTask)
    singleOf(::DeleteTask)
    singleOf(::OpenDataCapture)
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
