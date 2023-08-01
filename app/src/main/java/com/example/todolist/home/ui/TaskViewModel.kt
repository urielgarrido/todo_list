package com.example.todolist.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.detail.use_case.CompleteTaskUseCase
import com.example.todolist.detail.use_case.DeleteTaskUseCase
import com.example.todolist.home.use_case.AddTaskUseCase
import com.example.todolist.home.use_case.GetTasksUseCase
import com.example.todolist.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    val state = _state.asStateFlow()

    fun setSelectedTask(taskModel: TaskModel?) {
        _state.update {
            it.copy(taskSelected = taskModel)
        }
    }

    fun getTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { tasks ->
                _state.update {
                    it.copy(tasks = tasks)
                }
            }
        }
    }

    fun addTask(title: String, task: String) {
        viewModelScope.launch {
            addTaskUseCase(TaskModel(title = title, task = task, taskCreatedDate = getCurrentDateTime()))
        }
    }

    fun completeTask(taskModel: TaskModel) {
        viewModelScope.launch {
            val completedTask = taskModel.copy(isCompleted = true, taskCompletedDate = getCurrentDateTime())
            completeTaskUseCase(completedTask)
        }
    }

    fun deleteTask(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }
    }

    private fun getCurrentDateTime(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm 'hs'", Locale.getDefault())

        val date = dateFormat.format(calendar.time)
        val time = timeFormat.format(calendar.time)

        return "$date a las $time"
    }

}