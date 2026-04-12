package com.example.zadaciandroidakademija_3predavanje

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun ListScreen(onItemClick: (Int) -> Unit) {

    var titleColor by remember { mutableStateOf(Color.Black)}
    var descriptionColor by remember { mutableStateOf(Color.Red)}

    var dataList by remember { mutableStateOf(
        listOf(
            MyData(1, "Lamine", "Yamal", "https://assets.laliga.com/squad/2025/t178/p593109/1024x1113/p593109_t178_2025_1_001_000.png"),
            MyData(2, "Lionel", "Messi", "https://png.pngtree.com/png-vector/20250728/ourmid/pngtree-messi-argentina-high-energy-action-dynamic-pose-vibrant-colors-intense-lighting-png-image_16885439.webp"),
            MyData(3, "Neymar", "JR", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTT6-WzA5AQMGFE0Xv1-9YvPVr9jGaEtKoEOw&s"),
            MyData(4, "Cristiano", "Ronaldo", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5ZiR1SK6hXCz6HFILXD3lNI1h3-dQggVygg&s")
        )
    ) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TitleText("Sofascore", titleColor)

            DescriptionText("Best football players!", descriptionColor)

            Row {
                CustomButton("Change color",
                    {
                        titleColor = Color.Green
                        descriptionColor = Color.Blue
                },
                    icon = Icons.Default.AccountCircle)

                CustomButton("Change order",
                    {
                    dataList = dataList.shuffled()
                },
                    icon = Icons.Default.Refresh)
            }

            MyItemList(dataList) { item ->
                onItemClick(item.ID)
            }
        }
    }

}


