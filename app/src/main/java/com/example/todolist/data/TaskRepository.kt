package com.example.todolist.data

import com.example.todolist.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TaskModel>> = taskDao.getTasks().map { items ->
        items.map {
            TaskModel(it.id, it.title, it.task, it.isCompleted)
        }
    }

    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(taskModel.toEntity())
    }

    suspend fun completeTask(taskModel: TaskModel) {
        taskDao.completeTask(taskModel.toEntity())
    }

    suspend fun delete(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toEntity())
    }
}

fun TaskModel.toEntity(): TaskEntity {
    return TaskEntity(this.id, this.title, this.task, this.isCompleted)
}