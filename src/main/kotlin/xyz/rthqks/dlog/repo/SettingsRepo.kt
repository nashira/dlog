package xyz.rthqks.dlog.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import xyz.rthqks.alog.db.Setting
import xyz.rthqks.alog.db.SettingQueries

class SettingsRepo(
    private val settingQueries: SettingQueries
) {
    private val coroutineScope = CoroutineScope(Job())
    private val _settings = settingQueries.selectAll().asFlow().mapToList(coroutineScope.coroutineContext)
    val settings: Flow<Settings> = _settings.map { Settings() }

    fun set() {
        coroutineScope.launch {
            settingQueries.insert(Setting(0, "", "", "{}"))
        }
    }
}
