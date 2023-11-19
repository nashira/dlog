package xyz.rthqks.dlog.logic.task

import xyz.rthqks.dlog.repo.Task
import xyz.rthqks.dlog.repo.TaskRepo

class CreateTask(
    private val taskRepo: TaskRepo
) {

    suspend operator fun invoke(task: Task) {
        taskRepo.create(task)
    }
}