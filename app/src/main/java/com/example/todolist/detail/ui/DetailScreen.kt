package com.example.todolist.detail.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.model.TaskModel
import com.example.todolist.ui.theme.Delete

@Composable
fun DetailScreen(
    taskSelected: TaskModel,
    onBackPress: () -> Unit,
    completeTask: (TaskModel) -> Unit,
    deleteTask: (TaskModel) -> Unit
) {

    BackHandler(onBack = onBackPress)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = taskSelected.title, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = taskSelected.task)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Creada el ${taskSelected.taskCreatedDate}", fontSize = 12.sp)

        if (!taskSelected.isCompleted) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { completeTask(taskSelected) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Completar tarea", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Completada el ${taskSelected.taskCompletedDate}", fontSize = 12.sp)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = { deleteTask(taskSelected) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Delete)
        ) {
            Text(text = "Borrar tarea", color = Color.White)
        }
    }
}