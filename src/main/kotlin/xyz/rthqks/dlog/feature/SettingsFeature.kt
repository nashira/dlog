package xyz.rthqks.dlog.feature

import xyz.rthqks.dlog.repo.Task

class SettingsFeature(
    private val task: Task,
    private val windowClose: () -> Unit,
) : Feature() {

    fun onWindowClose() {
        stop()
        windowClose()
    }
}