package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.detail.ui.DetailScreen
import com.example.todolist.home.ui.HomeScreen
import com.example.todolist.home.ui.TaskViewModel
import com.example.todolist.model.TaskModel
import com.example.todolist.ui.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val viewModel: TaskViewModel = hiltViewModel()

                    fun navigateHome() {
                        navController.navigate("home") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    }

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(Unit) {
                                viewModel.getTasks()
                            }

                            LaunchedEffect(key1 = state.taskSelected) {
                                if (state.taskSelected != null) {
                                    navController.navigate("detail")
                                }
                            }

                            fun createTask(title: String, task: String) {
                                viewModel.addTask(title, task)
                            }

                            fun selectTask(taskModel: TaskModel) {
                                viewModel.setSelectedTask(taskModel)
                            }

                            HomeScreen(state.tasks, ::createTask, ::selectTask)
                        }
                        composable("detail") {
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            fun cleanTaskSelected() {
                                viewModel.setSelectedTask(null)
                                navigateHome()
                            }

                            fun completeTask(taskModel: TaskModel) {
                                viewModel.setSelectedTask(null)
                                viewModel.completeTask(taskModel)
                                navigateHome()
                            }

                            fun deleteTask(taskModel: TaskModel) {
                                viewModel.setSelectedTask(null)
                                viewModel.deleteTask(taskModel)
                                navigateHome()
                            }

                            DetailScreen(state.taskSelected!!, ::cleanTaskSelected, ::completeTask, ::deleteTask)
                        }
                    }

                }
            }
        }
    }
}