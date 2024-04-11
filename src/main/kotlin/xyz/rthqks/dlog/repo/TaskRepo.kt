package xyz.rthqks.dlog.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope
import xyz.rthqks.dlog.db.TaskEnt
import xyz.rthqks.dlog.db.TaskEntQueries

class TaskRepo(
    private val taskQueries: TaskEntQueries
) {
    suspend fun create(task: Task) {
        taskQueries.insert(TaskEnt(-1, task.type.name, task.fileName))
    }

    suspend fun update(task: Task) {
        taskQueries.update(task.type.name, task.fileName, task.id)
    }

    suspend fun delete(id: Long) {
        taskQueries.delete(id)
    }

    fun getTasks(): Flow<List<Task>> = taskQueries.getAll()
        .asFlow()
        .mapToList(Dispatchers.IO)
        .map { list ->
            list.map {
                when (it.type) {
                    Task.Type.ViewAlogChart.name -> Task(it.id, Task.Type.ViewAlogChart, it.fileName)
                    Task.Type.EditSettings.name -> Task(it.id, Task.Type.EditSettings)
                    Task.Type.ReplayAlogChart.name -> Task(it.id, Task.Type.ReplayAlogChart, it.fileName)
                    Task.Type.ReplaySettings.name -> Task(it.id, Task.Type.EditSettings)
                    Task.Type.DataCapture.name -> Task(it.id, Task.Type.DataCapture, it.fileName)
                    else -> error("unknown type $it")
                }
            }
        }

}

data class Task(
    val id: Long,
    val type: Type,
    val fileName: String? = null,
) {
    sealed class Type(val name: String) {
        data object EditSettings : Type("edit_settings")
        data object ViewAlogChart : Type("view_alog_chart")
        data object ReplayAlogChart : Type("replay_alog_chart")
        data object ReplaySettings : Type("replay_alog_chart_settings")
        data object DataCapture: Type("data_capture")
    }
}

class TaskScope : KoinScopeComponent {
    override val scope: Scope by getOrCreateScope()
}
