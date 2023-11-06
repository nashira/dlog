package xyz.rthqks.alog.logic

import xyz.rthqks.alog.intent.ChangeSetting
import xyz.rthqks.alog.intent.Intent
import xyz.rthqks.alog.repo.SettingsRepo

class SettingsStateReducer(
    parent: Reducer?,
    val settingsRepo: SettingsRepo,
) : Reducer(parent) {

    override fun handleIntent(intent: Intent) = when (intent) {
        is ChangeSetting -> {
            println("change setting ${intent.setting}")
        }
        else -> Unit
    }
}