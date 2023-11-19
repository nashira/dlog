package xyz.rthqks.dlog.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.ui.chart.ChartWindow
import xyz.rthqks.dlog.ui.settings.SettingsWindow
import xyz.rthqks.dlog.viewmodel.app.AppViewModel


@Composable
fun WindowManager(vm: AppViewModel) {
    val taskStates by vm.tasks.collectAsState()
    taskStates.forEach { state ->
        val task = state.task
        when (task) {
            is Task.EditSettings -> SettingsWindow(vm.get { parametersOf(task.id) })
            is Task.ViewAlogChart -> ChartWindow(vm.get { parametersOf(task) })
        }
        println("task $task")
    }
}