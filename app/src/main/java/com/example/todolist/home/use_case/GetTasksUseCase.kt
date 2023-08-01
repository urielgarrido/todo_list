package com.example.todolist.home.use_case

import com.example.todolist.data.TaskRepository
import com.example.todolist.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val repository: TaskRepository) {

    operator fun invoke(): Flow<List<TaskModel>> = repository.tasks
}