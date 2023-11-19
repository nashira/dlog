package xyz.rthqks.dlog.logic.task

import xyz.rthqks.dlog.repo.TaskRepo

class DeleteTask(
    private val taskRepo: TaskRepo
) {

    suspend operator fun invoke(taskId: Long) {
        taskRepo.delete(taskId)
    }
}