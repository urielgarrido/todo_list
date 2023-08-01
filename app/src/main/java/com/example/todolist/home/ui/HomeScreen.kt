package com.example.todolist.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todolist.R
import com.example.todolist.model.TaskModel
import com.example.todolist.ui.theme.Complete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(tasks: List<TaskModel>, createTask: (String, String) -> Unit, onTaskPress: (TaskModel) -> Unit) {

    var dialogVisible: Boolean by remember { mutableStateOf(false) }

    fun changeDialogVisibility() {
        dialogVisible = !dialogVisible
    }

    var title by remember { mutableStateOf("") }
    var task by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = { CreateTaskFAB(::changeDialogVisibility) }
    ) { paddingValues ->

        if (dialogVisible) {
            Dialog(onDismissRequest = ::changeDialogVisibility) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp), verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Agrege su tarea",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        maxLines = 1,
                        placeholder = {
                            Text(text = "Título")
                        },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = task,
                        onValueChange = { task = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 100.dp),
                        placeholder = {
                            Text(text = "Tarea")
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        createTask(title, task)
                        changeDialogVisibility()
                        title = ""
                        task = ""
                    }, modifier = Modifier.fillMaxWidth(), enabled = title.isNotBlank() && task.isNotBlank()) {
                        Text(text = "Crear")
                    }
                }
            }
        }

        if (tasks.isEmpty()) ShowEmptyTasks()
        else
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = paddingValues) {
                items(tasks) {
                    TaskItem(taskModel = it) { taskModelSelected -> onTaskPress(taskModelSelected) }
                }
            }

    }
}

@Composable
fun ShowEmptyTasks() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.empty_task),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CreateTaskFAB(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun TaskItem(taskModel: TaskModel, onTaskPress: (TaskModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onTaskPress(taskModel) },
        colors = CardDefaults.cardColors(
            containerColor = if (taskModel.isCompleted) {
                Complete
            } else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 120.dp)
                .padding(8.dp)
        )
        {
            Row {
                Text(
                    text = taskModel.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                )
                Text(
                    text = if (taskModel.isCompleted) "Completada" else "Pendiente",
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = taskModel.task, overflow = TextOverflow.Ellipsis)
        }
    }
}