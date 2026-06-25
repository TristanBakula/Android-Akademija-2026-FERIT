package com.example.taskie.ui.ListScreen

import android.R.attr.maxLines
import android.R.attr.onClick
import android.R.attr.text
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskie.R
import com.example.taskie.data.model.Task
import org.koin.androidx.compose.koinViewModel


@Composable
fun TasksListScreen(
    onAddClick: () -> Unit,
    onTaskClick: (String?) -> Unit,
    onLogoutClick: () -> Unit
) {
    val viewModel: TasksScreenViewModel = koinViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getTasks()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val uiState = viewModel.tasksListUIState.value

    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    var showLogoutDialog by remember { mutableStateOf(false) }

    if (taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { taskToDelete = null },
            title = { Text("Delete task") },
            text = { Text("Do you want to delete \"${taskToDelete?.title ?: "this task"}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    taskToDelete?.id?.let { viewModel.deleteTask(it) }
                    taskToDelete = null
                }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { taskToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier.align(Alignment.CenterStart),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
                
                Text(
                    text = stringResource(R.string.title_tasks_list),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )



                Button(
                    onClick = { onAddClick() },
                    modifier = Modifier.align(Alignment.CenterEnd),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.button_add_task),
                        tint = Color.Black,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("Logout") },
                    text = { Text("Are you sure you want to logout?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            viewModel.logout { onLogoutClick() }
                        }) {
                            Text("Yes", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }

            val currentFilter by viewModel.currentFilter.collectAsState()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterType.entries.forEach { filter ->
                    Button(
                        onClick = { viewModel.setFilter(filter) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentFilter == filter) Color.Red else Color.Gray
                        )
                    ) {
                        Text(filter.name)
                    }
                }
            }

            when (uiState) {
                is TasksListUiState.Loading -> LoadingScreen()
                is TasksListUiState.Loaded -> {
                    if (uiState.list.isEmpty()) {
                        Text(
                            "List is empty, add your first task!",
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        MyTasksList(
                            tasks = uiState.list,
                            onTaskClick = onTaskClick,
                            onTaskLongClick = { task -> taskToDelete = task },
                            onCheckboxClick = { id, isCompleted ->
                                viewModel.toggleTaskCompleted(id, isCompleted)
                            },
                            getTaskDate = { id -> viewModel.getTaskDate(id) }
                        )
                    }
                }
                is TasksListUiState.Failure -> ErrorScreen(uiState.messageResId, viewModel::getTasks)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    task: Task,
    onClick: (Task) -> Unit,
    onLongClick: (Task) -> Unit,
    onCheckboxClick: (String, Boolean) -> Unit,
    getTaskDate: (String) -> String?
) {

    val dateText = task.id?.let { getTaskDate(it) } ?: ""
    val borderColor = if (task.isCompleted == true) Color.Green else MaterialTheme.colorScheme.outline


    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                onClick = { onClick(task) },
                onLongClick = { onLongClick(task) }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.Transparent),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(borderColor)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title ?: "No title",
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = dateText,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Checkbox(
                checked = task.isCompleted == true,
                onCheckedChange = { isChecked ->
                    task.id?.let { onCheckboxClick(it, isChecked) }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green
                )
            )
        }
    }
}

@Composable
fun MyTasksList(
    tasks: List<Task>,
    onTaskClick: (String?) -> Unit,
    onTaskLongClick: (Task) -> Unit,
    onCheckboxClick: (String, Boolean) -> Unit,
    getTaskDate: (String) -> String?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tasks) { item ->
            TaskItem(
                task = item,
                onClick = { onTaskClick(it.id ?: "-1") },
                onLongClick = onTaskLongClick,
                onCheckboxClick = onCheckboxClick,
                getTaskDate = getTaskDate
            )
        }
    }
}