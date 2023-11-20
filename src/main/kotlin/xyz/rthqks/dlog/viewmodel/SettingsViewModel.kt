package xyz.rthqks.dlog.viewmodel

import xyz.rthqks.dlog.repo.Task

class SettingsViewModel(
    private val task: Task,
    private val windowClose: () -> Unit,
) : ViewModel() {

    fun onWindowClose() {
        close()
        windowClose()
    }
}