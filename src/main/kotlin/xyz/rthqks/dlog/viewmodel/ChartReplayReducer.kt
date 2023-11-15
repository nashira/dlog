package xyz.rthqks.dlog.viewmodel

import xyz.rthqks.dlog.intent.Intent
import xyz.rthqks.dlog.state.ChartState

class ChartReplayReducer(
    parent: Reducer<*>
): Reducer<ChartState>(parent) {
    override val state: ChartState
        get() = TODO("Not yet implemented")

    override fun handleIntent(intent: Intent) {

    }
}