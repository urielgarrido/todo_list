package com.example.todolist.home.ui

import com.example.todolist.model.TaskModel

data class TaskState(
    val tasks: List<TaskModel> = emptyList(),
    val taskSelected: TaskModel? = null
)
