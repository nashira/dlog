package xyz.rthqks.alog.chart

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import xyz.rthqks.alog.app.state.ChartWindowState
import xyz.rthqks.alog.logic.CloseWindow
import xyz.rthqks.alog.logic.Intent
import xyz.rthqks.alog.logic.Reducer
import xyz.rthqks.alog.model.AlogDocument
import xyz.rthqks.alog.settings.state.SettingsState
import xyz.rthqks.alog.usecase.*

class ChartWindowReducer(
    parent: Reducer<*>,
    private val alog: AlogDocument,
    private val createChartStateFromAlog: CreateChartStateFromAlog,
    private val getSettings: GetSettings,
) : Reducer<ChartWindowState>(parent) {
    private val clockState = mutableStateOf("")
    private val titleState = mutableStateOf("")
    private val chartState = mutableStateOf(ChartState(emptyList(), emptyList(), emptyList()))
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