package xyz.rthqks.alog.usecase

import xyz.rthqks.alog.settings.Setting
import xyz.rthqks.alog.settings.SettingsRepo

class SetSetting(
    private val settingsRepo: SettingsRepo
) {

    operator fun invoke(setting: Setting) = settingsRepo.set(setting)
}