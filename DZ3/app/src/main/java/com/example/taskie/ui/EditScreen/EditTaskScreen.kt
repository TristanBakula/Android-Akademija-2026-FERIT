package com.example.note_app.ui.EditScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.note_app.R
import com.example.note_app.ui.LoginScreen.LoginViewModel


@Composable
fun EditTaskScreen(
    taskId: String,
    onBack: () -> Unit,
) {

    val context = LocalContext.current

    val viewModel: EditTaskScreenViewModel = viewModel(
        factory = EditTaskScreenViewModel.provideFactory(context)
    )

    LaunchedEffect(taskId) {
        viewModel.initTask(taskId)
    }

    LaunchedEffect(viewModel.isSaved) {  // tek kad se spremi vraca ga natrag
        if (viewModel.isSaved) {
            onBack()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp)

        ) {
            if (viewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = viewModel.errorMessage,        color = Color.Red,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = { onBack() },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.button_add_task),
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            }

            OutlinedTextField(
                value = viewModel.titleText,
                onValueChange = { viewModel.titleText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(80.dp),
                textStyle = TextStyle(
                    fontSize = 22.sp,
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red,
                    cursorColor = Color.Red,
                    focusedLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
            )

            OutlinedTextField(
                value = viewModel.bodyText,
                onValueChange = { viewModel.bodyText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 16.dp),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Red,
                    cursorColor = Color.Red,
                    focusedLabelColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = false,
                maxLines = 10
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.saveTask()
                          },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = stringResource(R.string.button_done))
            }

        }
    }
}