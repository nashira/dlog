package xyz.rthqks.dlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import xyz.rthqks.dlog.state.ChartWindowState
import xyz.rthqks.dlog.intent.CloseWindow
import xyz.rthqks.dlog.logic.CreateChartStateFromAlog
import xyz.rthqks.dlog.logic.GetSettings
import xyz.rthqks.dlog.intent.Intent
import xyz.rthqks.dlog.io.AlogDocument
import xyz.rthqks.dlog.state.ChartState
import xyz.rthqks.dlog.state.SettingsState

class ChartWindowReducer(
    parent: Reducer<*>,
    private val alog: AlogDocument,
    private val createChartStateFromAlog: CreateChartStateFromAlog,
    private val getSettings: GetSettings,
) : Reducer<ChartWindowState>(parent) {
    private val clockState = mutableStateOf("")
    private val titleState = mutableStateOf("")
    private val chartState = mutableStateOf(
        ChartState(
            emptyList(),
            emptyList(),
            emptyList()
        )
    )
    private val coroutineScope = CoroutineScope(Job())
    override val state: ChartWindowState = ChartWindowState(titleState, chartState, clockState)

    init {
        coroutineScope.launch {
            getSettings(this).collect(::updateState)
        }
    }

    private fun updateState(settingsState: SettingsState) {
        println("updateState")
        val chart = createChartStateFromAlog(alog)
        val title = "${alog.title} - ${alog.fileName}"
        titleState.value = title
        chartState.value = chart
    }

    override fun handleIntent(intent: Intent) = when (intent) {
        is CloseWindow -> coroutineScope.cancel()
        else -> Unit
    }
}