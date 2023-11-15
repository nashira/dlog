package xyz.rthqks.dlog.state

import xyz.rthqks.dlog.intent.Intent

data class MenuState(
    val items: Map<String, Pair<String, Intent>>
)