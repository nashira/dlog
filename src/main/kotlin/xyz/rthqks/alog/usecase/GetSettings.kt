package xyz.rthqks.alog.usecase

import kotlinx.coroutines.flow.StateFlow
import xyz.rthqks.alog.settings.Setting
import xyz.rthqks.alog.settings.SettingsRepo

class GetSettings(
    private val settingsRepo: SettingsRepo
) {

    operator fun invoke(): StateFlow<Setting> = settingsRepo.setting
}