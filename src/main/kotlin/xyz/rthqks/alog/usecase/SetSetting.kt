package xyz.rthqks.alog.usecase

import xyz.rthqks.alog.settings.repo.Setting
import xyz.rthqks.alog.settings.repo.SettingsRepo

class SetSetting(
    private val settingsRepo: SettingsRepo
) {

    operator fun <T> invoke(setting: Setting<T>) = settingsRepo.set()
}