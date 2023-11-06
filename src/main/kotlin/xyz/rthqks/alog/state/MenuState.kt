package xyz.rthqks.alog.state

import xyz.rthqks.alog.intent.Intent

data class MenuState(
    val items: Map<String, Pair<String, Intent>>
)