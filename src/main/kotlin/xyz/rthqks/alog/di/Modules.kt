package xyz.rthqks.alog.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.rthqks.alog.logic.AppStateReducer
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
}
