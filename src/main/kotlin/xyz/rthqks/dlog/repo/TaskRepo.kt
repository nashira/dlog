package xyz.rthqks.dlog.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.rthqks.dlog.db.TaskEnt
import xyz.rthqks.dlog.db.TaskEntQueries

class TaskRepo(
    private val taskQueries: TaskEntQueries
) {
    suspend fun create(task: Task) {
        val fileName = (task as? Task.ViewAlogChart)?.fileName
        taskQueries.insert(TaskEnt(-1, task.type.name, fileName))
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
                    Task.Type.ViewAlogChart.name -> Task.ViewAlogChart(it.id, it.fileName ?: "")
                    Task.Type.EditSettings.name -> Task.EditSettings(it.id)
                    else -> error("unknown type $it")
                }
            }
        }

}

sealed class Task(
    val type: Type,
) {
    data class ViewAlogChart(val id: Long, val fileName: String) : Task(Type.ViewAlogChart)
    data class EditSettings(val id: Long) : Task(Type.EditSettings)

    sealed class Type(val name: String) {
        data object EditSettings : Type("edit_settings")
        data object ViewAlogChart : Type("view_alog_chart")
    }
}