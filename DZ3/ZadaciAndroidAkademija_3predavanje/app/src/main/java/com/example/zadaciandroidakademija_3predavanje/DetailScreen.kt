package com.example.zadaciandroidakademija_3predavanje

import android.icu.text.CaseMap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants


@Composable
fun DetailScreen(data: MyData, onBack: () -> Unit) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                TitleText(data.fullTitle, Color.Red)
                DescriptionText("This is something about ${data.fullTitle}.", Color.Black)

                AsyncImage(
                    model = data.imageLink,
                    contentDescription = "Player image",
                    modifier = Modifier
                        .height(600.dp)
                        .width(600.dp)
                        .padding(50.dp)
                )
            }

            CustomButton("Back", onBack , icon = Icons.AutoMirrored.Filled.ArrowBack)
        }

    }

}