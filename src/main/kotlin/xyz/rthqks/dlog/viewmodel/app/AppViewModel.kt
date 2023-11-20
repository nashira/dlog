package xyz.rthqks.dlog.viewmodel.app

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import xyz.rthqks.dlog.logic.task.GetTasks
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.repo.TaskScope
import xyz.rthqks.dlog.state.TaskState
import xyz.rthqks.dlog.viewmodel.ViewModel

class AppViewModel(
    getTasks: GetTasks
) : ViewModel(), KoinComponent {
    val tasks: StateFlow<List<TaskState>> = getTasks()
        .map { list ->
            list.map {
                TaskState(it)
            }
        }
        .stateIn(coroutineScope, SharingStarted.Eagerly, emptyList())

    inline fun <reified T : ViewModel> getVm(task: Task): T {
        return get<TaskScope> { parametersOf(task) }.get<T> { parametersOf(task) }
    }
}
