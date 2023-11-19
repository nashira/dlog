package xyz.rthqks.dlog.logic.task

import xyz.rthqks.dlog.repo.TaskRepo

class GetTasks(
    private val taskRepo: TaskRepo
) {

    operator fun invoke() = taskRepo.getTasks()
}