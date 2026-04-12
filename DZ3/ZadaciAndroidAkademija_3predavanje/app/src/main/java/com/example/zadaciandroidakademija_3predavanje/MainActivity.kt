package com.example.zadaciandroidakademija_3predavanje

import android.R.attr.data
import android.R.attr.text
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerBasedShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.zadaciandroidakademija_3predavanje.ui.theme.ZadaciAndroidAkademija_3predavanjeTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ZadaciAndroidAkademija_3predavanjeTheme {

                var dataList by remember { mutableStateOf(
                    listOf(
                        MyData(1, "Lamine", "Yamal", "https://assets.laliga.com/squad/2025/t178/p593109/1024x1113/p593109_t178_2025_1_001_000.png"),
                        MyData(2, "Lionel", "Messi", "https://png.pngtree.com/png-vector/20250728/ourmid/pngtree-messi-argentina-high-energy-action-dynamic-pose-vibrant-colors-intense-lighting-png-image_16885439.webp"),
                        MyData(3, "Neymar", "JR", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTT6-WzA5AQMGFE0Xv1-9YvPVr9jGaEtKoEOw&s"),
                        MyData(4, "Cristiano", "Ronaldo", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5ZiR1SK6hXCz6HFILXD3lNI1h3-dQggVygg&s")
                    )
                ) }

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "list") {

                    // List screen
                    composable("list") {
                        ListScreen(onItemClick = { selectedId ->
                            navController.navigate("detail/$selectedId")
                        })
                    }

                    composable(
                        "detail/{playerId}",
                        arguments = listOf(navArgument("playerId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("playerId") ?: 0
                        val data = dataList.first { it.ID == id }

                        DetailScreen(data = data, onBack = { navController.popBackStack() })
                    }
                }

            }
        }
    }
}



data class MyData(
    val ID: Int,
    val name: String,
    val surname: String,
    val imageLink: String
) {
    val fullTitle: String
        get() = "$name $surname"

    val fullDescription: String
        get() = "Hello, my name is $name $surname."

}


@Composable
fun TitleText(title: String, colorTitle: Color) {
    Text(
        text = title,
        modifier = Modifier.padding(10.dp),
        fontSize = 50.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Cursive,
        color = colorTitle
    )
}

@Composable
fun DescriptionText(text: String, descriptionColor: Color) {
    Text(
        text = text,
        modifier = Modifier.padding(10.dp),
        fontSize = 30.sp,
        lineHeight = 30.sp,
        maxLines = 3,
        color = descriptionColor
    )
}

@Composable
fun CustomButton(text: String, onClick: () -> Unit, icon: ImageVector) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .height(36.dp)   // sve unutar Row imaju jednaku širinu
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text
        )
    }
}

@Composable
fun ItemCard(
    data: MyData,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(16.dp),
        //colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(8.dp)
            ) {
                TitleText(data.fullTitle, Color.Red)
                DescriptionText(data.fullDescription, Color.Black)
                Row {
                    CustomButton("Favorite", {}, Icons.Default.Favorite)
                    CustomButton("Save", {}, Icons.Default.Edit)
                }
            }

            AsyncImage(
                model = data.imageLink,
                contentDescription = "Player image",
                modifier = Modifier
                    .weight(0.4f)
                    .height(250.dp)
            )
        }
    }
}


@Composable
fun MyItemList(
    data: List<MyData>,
    onItemClick: (MyData) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)  // razmak
    ) {
        items(data) { item ->
            ItemCard(item) { onItemClick(item) }
        }
    }
}