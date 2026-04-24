package com.example.note_app.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.note_app.Note
import com.example.note_app.presentation.EditScreenViewModel
import androidx.compose.ui.res.stringResource
import com.example.note_app.R


@Composable
fun DetailScreen(
    viewModel: EditScreenViewModel,
    onBack: () -> Unit,
) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp)

        ) {
            Button(
                onClick = { onBack() },
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.button_add_note),
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
                value = viewModel.descriptionText,
                onValueChange = { viewModel.descriptionText = it },
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
                    viewModel.saveNote()
                    onBack()
                          },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = stringResource(R.string.button_done))
            }

        }
    }
}