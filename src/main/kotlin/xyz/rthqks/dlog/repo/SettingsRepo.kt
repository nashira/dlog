package xyz.rthqks.dlog.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import xyz.rthqks.dlog.db.SettingEnt
import xyz.rthqks.dlog.db.SettingEntQueries

class SettingsRepo(
    private val settingQueries: SettingEntQueries
) {
    private val coroutineScope = CoroutineScope(Job())
    private val _settings = settingQueries.selectAll().asFlow().mapToList(coroutineScope.coroutineContext)
    val settings: Flow<Settings> = _settings.map { Settings() }

    fun set() {
        coroutineScope.launch {
            settingQueries.insert(SettingEnt(0, "", "", "{}"))
        }
    }
}
