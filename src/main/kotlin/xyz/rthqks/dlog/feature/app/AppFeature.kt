package xyz.rthqks.dlog.feature.app

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import xyz.rthqks.dlog.feature.Feature
import xyz.rthqks.dlog.logic.task.DeleteTask
import xyz.rthqks.dlog.logic.task.GetTasks
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.repo.TaskScope
import xyz.rthqks.dlog.state.TaskState

class AppFeature(
    getTasks: GetTasks,
    private val deleteTask: DeleteTask,
) : Feature(), KoinComponent {
    private val taskScopes = mutableMapOf<Task, TaskScope>()
    val tasks: StateFlow<List<TaskState>> = getTasks()
        .map { list ->
            list.map {
                TaskState(it)
            }
        }
        .stateIn(coroutineScope, SharingStarted.Eagerly, emptyList())

    inline fun <reified T : Feature> getFeature(task: Task): T {
        val scope = taskScope(task)
        return scope.get<T> { parametersOf(task, { onWindowClose(task) }) }
    }

    fun onWindowClose(task: Task) {
        taskScopes.remove(task)?.scope?.close()
        coroutineScope.launch { deleteTask(task.id) }
    }

    fun taskScope(task: Task): TaskScope = taskScopes.getOrPut(task) {
        get()
    }
}
