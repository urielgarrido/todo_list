package com.example.todolist.model

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val title: String,
    val task: String,
    val isCompleted: Boolean = false,
    val taskCreatedDate: String,
    val taskCompletedDate: String? = null,
)
