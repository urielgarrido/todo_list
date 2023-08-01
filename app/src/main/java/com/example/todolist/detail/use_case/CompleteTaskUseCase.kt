package com.example.todolist.detail.use_case

import com.example.todolist.data.TaskRepository
import com.example.todolist.model.TaskModel
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        repository.completeTask(taskModel)
    }
}