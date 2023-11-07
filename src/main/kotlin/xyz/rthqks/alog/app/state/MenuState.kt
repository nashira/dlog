package xyz.rthqks.alog.app.state

import xyz.rthqks.alog.intent.Intent

data class MenuState(
    val items: Map<String, Pair<String, Intent>>
)