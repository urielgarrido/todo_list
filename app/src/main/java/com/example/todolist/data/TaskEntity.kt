package com.example.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val task: String,
    val isCompleted: Boolean,
    val taskCreatedDate: String,
    val taskCompletedDate: String?,
)
