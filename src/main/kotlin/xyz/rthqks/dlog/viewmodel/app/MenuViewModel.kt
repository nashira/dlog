package xyz.rthqks.dlog.viewmodel.app

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import xyz.rthqks.dlog.intent.Intent
import xyz.rthqks.dlog.logic.task.CreateTask
import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.repo.Task.Type
import xyz.rthqks.dlog.repo.Task.Type.ReplayAlogChart
import xyz.rthqks.dlog.repo.Task.Type.ViewAlogChart
import xyz.rthqks.dlog.viewmodel.ViewModel
import java.io.File

class MenuViewModel(
    private val createTask: CreateTask
) : ViewModel() {
    val filePicker = mutableStateOf(false)
    private var filePickerIntent: Menu? = null

    fun handle(menuIntent: Menu) {
        println("menu intent $menuIntent")
        when (menuIntent) {
            is Menu.FileOpen,
            is Menu.FileReplay -> {
                filePicker.value = true
                filePickerIntent = menuIntent
            }

            is Menu.SelectFiles -> {
                filePicker.value = false
                createFileOpenTask(menuIntent)
            }

            is Menu.SettingsEdit -> coroutineScope.launch {
                createTask(Task(-1, Type.EditSettings))
            }
        }
    }

    private fun createFileOpenTask(menuIntent: Menu.SelectFiles) {
        coroutineScope.launch {
            when (filePickerIntent) {
                is Menu.FileOpen -> {
                    menuIntent.files.forEach {
                        createTask(Task(-1, ViewAlogChart, it.absolutePath))
                    }
                }

                is Menu.FileReplay -> {
                    menuIntent.files.forEach {
                        createTask(Task(-1, ReplayAlogChart, it.absolutePath))
                    }
                }

                else -> {}
            }
        }
    }
}

sealed class Menu : Intent {
    data class SelectFiles(val files: List<File>) : Menu()
    data object FileOpen : Menu()
    data object FileReplay : Menu()
    data object SettingsEdit : Menu()
}
