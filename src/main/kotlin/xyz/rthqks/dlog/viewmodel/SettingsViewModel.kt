package xyz.rthqks.dlog.viewmodel

import androidx.compose.runtime.mutableStateOf
import xyz.rthqks.dlog.logic.task.DeleteTask
import xyz.rthqks.dlog.repo.Task

class SettingsViewModel(task: Task, deleteTask: DeleteTask) : TaskViewModel(task.id, deleteTask) {
    override val titleState = mutableStateOf("Settings")
}