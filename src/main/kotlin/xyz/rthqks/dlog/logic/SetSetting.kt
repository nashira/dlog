package xyz.rthqks.dlog.logic

import xyz.rthqks.dlog.repo.Setting
import xyz.rthqks.dlog.repo.SettingsRepo

class SetSetting(
    private val settingsRepo: SettingsRepo
) {

    operator fun <T> invoke(setting: Setting<T>) = settingsRepo.set()
}