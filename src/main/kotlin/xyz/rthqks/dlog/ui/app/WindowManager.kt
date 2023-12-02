package xyz.rthqks.dlog.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.ui.chart.ChartReplayWindow
import xyz.rthqks.dlog.ui.chart.ChartWindow
import xyz.rthqks.dlog.ui.settings.SettingsWindow
import xyz.rthqks.dlog.viewmodel.app.AppViewModel


@Composable
fun WindowManager(vm: AppViewModel) {
    val taskStates by vm.tasks.collectAsState()
    taskStates.forEach { state ->
        val task = state.task
        when (task.type) {
            is Task.Type.EditSettings -> SettingsWindow(vm.getVm(task))
            is Task.Type.ViewAlogChart -> ChartWindow(vm.getVm(task))
            is Task.Type.ReplayAlogChart -> ChartReplayWindow((vm.getVm(task)))
            is Task.Type.ReplaySettings -> SettingsWindow(vm.getVm(task))
        }
        println("task $task")
    }
}
