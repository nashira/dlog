package xyz.rthqks.alog.chart

import xyz.rthqks.alog.app.state.ChartWindowState
import xyz.rthqks.alog.intent.Intent
import xyz.rthqks.alog.logic.Reducer

class ChartWindowReducer(
    parent: Reducer<*>,
    override val state: ChartWindowState
) : Reducer<ChartWindowState>(parent) {

    override fun handleIntent(intent: Intent) { }
}