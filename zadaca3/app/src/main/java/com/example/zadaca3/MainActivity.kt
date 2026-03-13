package com.example.zadaca3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.zadaca3.ui.theme.Zadaca3Theme
import androidx.compose.ui.unit.dp

data class Dessert(
    val imageId: Int,
    val price: Int,
    val startProductionAmount: Int
)

class MainActivity : ComponentActivity() {

    private val desserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.icecream, 50, 100)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Zadaca3Theme {
                DessertClickerApp(desserts)
            }
        }
    }
}

fun determineDessertToShow(desserts: List<Dessert>, dessertsSold: Int): Dessert {
    var dessertToShow = desserts.first()

    for (dessert in desserts) {
        if (dessertsSold >= dessert.startProductionAmount) {
            dessertToShow = dessert
        } else {
            break
        }
    }

    return dessertToShow
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DessertClickerApp(desserts: List<Dessert>) {

    var revenue by remember { mutableIntStateOf(0) }
    var dessertsSold by remember { mutableIntStateOf(0) }

    var currentDessert by remember {
        mutableStateOf(desserts.first())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dessert Clicker") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(currentDessert.imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable {

                        revenue += currentDessert.price
                        dessertsSold++

                        currentDessert =
                            determineDessertToShow(desserts, dessertsSold)
                    }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = "Desserts sold: $dessertsSold",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total Revenue: $$revenue",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}