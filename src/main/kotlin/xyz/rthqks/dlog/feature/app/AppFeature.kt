package xyz.rthqks.dlog.feature.app

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import xyz.rthqks.dlog.logic.task.DeleteTask
import xyz.rthqks.dlog.logic.task.GetTasks
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.repo.TaskScope
import xyz.rthqks.dlog.state.TaskState
import xyz.rthqks.dlog.feature.Feature

class AppFeature(
    getTasks: GetTasks,
    private val deleteTask: DeleteTask,
) : Feature(), KoinComponent {
    val tasks: StateFlow<List<TaskState>> = getTasks()
        .map { list ->
            list.map {
                TaskState(it)
            }
        }
        .stateIn(coroutineScope, SharingStarted.Eagerly, emptyList())

    inline fun <reified T : Feature> getVm(task: Task): T {
        val scope = get<TaskScope> { parametersOf(task) }
        return scope.get<T> { parametersOf(task, { onWindowClose(scope) }) }
    }

    fun onWindowClose(scope: TaskScope) {
        scope.scope.close()
        coroutineScope.launch { deleteTask(scope.task.id) }
    }
}
