package xyz.rthqks.dlog.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.ui.chart.ChartReplayWindow
import xyz.rthqks.dlog.ui.chart.ChartWindow
import xyz.rthqks.dlog.ui.settings.SettingsWindow
import xyz.rthqks.dlog.feature.app.AppFeature
import xyz.rthqks.dlog.ui.chart.DataCaptureWindow


@Composable
fun WindowManager(vm: AppFeature) {
    val taskStates by vm.tasks.collectAsState()
    taskStates.forEach { state ->
        val task = state.task
        key(task) {
            when (task.type) {
                is Task.Type.EditSettings -> SettingsWindow(vm.getVm(task))
                is Task.Type.ViewAlogChart -> ChartWindow(vm.getVm(task))
                is Task.Type.ReplayAlogChart -> ChartReplayWindow((vm.getVm(task)))
                is Task.Type.ReplaySettings -> SettingsWindow(vm.getVm(task))
                is Task.Type.DataCapture -> DataCaptureWindow((vm.getVm(task)))
            }
        }
        println("task $task")
    }
}
