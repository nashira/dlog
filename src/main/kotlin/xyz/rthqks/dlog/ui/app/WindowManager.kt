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
fun WindowManager(app: AppFeature) {
    val taskStates by app.tasks.collectAsState()
    taskStates.forEach { state ->
        val task = state.task
        key(task) {
            when (task.type) {
                is Task.Type.EditSettings -> SettingsWindow(app.getFeature(task))
                is Task.Type.ViewAlogChart -> ChartWindow(app.getFeature(task))
                is Task.Type.ReplayAlogChart -> ChartReplayWindow((app.getFeature(task)))
                is Task.Type.ReplaySettings -> SettingsWindow(app.getFeature(task))
                is Task.Type.DataCapture -> DataCaptureWindow((app.getFeature(task)))
            }
        }
        println("task $task")
    }
}
