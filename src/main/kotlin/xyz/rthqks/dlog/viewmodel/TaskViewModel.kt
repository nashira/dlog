package xyz.rthqks.dlog.viewmodel

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.launch
import xyz.rthqks.dlog.intent.Intent
import xyz.rthqks.dlog.logic.task.DeleteTask

abstract class TaskViewModel(
    private val taskId: Long,
    private val deleteTask: DeleteTask
) : ViewModel() {
    abstract val titleState: MutableState<String>

    fun handle(taskIntent: TaskIntent) = when (taskIntent) {
        is TaskIntent.CloseWindow -> coroutineScope.launch {
            deleteTask(taskId)
        }
    }
}

sealed class TaskIntent : Intent {
    data object CloseWindow : TaskIntent()
}