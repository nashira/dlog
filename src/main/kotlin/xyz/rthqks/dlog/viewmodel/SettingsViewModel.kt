package xyz.rthqks.dlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import xyz.rthqks.dlog.logic.task.DeleteTask

class SettingsViewModel(taskId: Long, deleteTask: DeleteTask) : TaskViewModel(taskId, deleteTask) {
    override val titleState = mutableStateOf("Settings")
}