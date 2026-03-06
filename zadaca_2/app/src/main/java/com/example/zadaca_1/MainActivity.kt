package com.example.zadaca_1

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.zadaca_1.ui.theme.Zadaca_2Theme

class MainActivity : ComponentActivity() {

    // on create event (on activity init)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Zadaca_2Theme { // mozda vo zadaca_2 smeni go
                DictionaryScreen()
            }
        }
    }
}

/// fn to load file
fun loadDictionary(context: Context): Map<String, String> {

    val dictionary = mutableMapOf<String, String>()

    val inputStream = context.assets.open("dictionary.txt")
    val lines = inputStream.bufferedReader().readLines()

    for (line in lines) {

        val parts = line.split(",")

        if (parts.size == 2) {

            val english = parts[0].trim().lowercase()
            val macedonian = parts[1].trim().lowercase()

            dictionary[english] = macedonian
            dictionary[macedonian] = english
        }
    }

    return dictionary
}

// main screen (directory screen)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryScreen() {

    val context = LocalContext.current
    val dictionary = remember { loadDictionary(context) }

    var searchQuery by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    // old gooofy stuff
    val tags = remember {
        mutableStateListOf(
            "AndroidFP",
            "Deitel",
            "Google",
            "iPhoneFP",
            "JavaFP",
            "JavaHTP"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Macedonian - English Dictionary") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Enter word") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // search button

            Button(
                onClick = {

                    val search = searchQuery.lowercase()

                    result = dictionary[search]
                        ?: "Зборот не постои во речникот"

                }, // on click end
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Пребарај")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // result txt

            Text(
                text = "Result: $result",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // old screen (on predhondnoto)

            Text(
                text = "Tagged Searches",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(tags) { tag ->
                    TagItem(tag)
                }
            }

            Button(
                onClick = { tags.clear() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Tags")
            }
        }
    }
}

// tag card
@Composable
fun TagItem(tag: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(tag)

            TextButton(onClick = { }) {
                Text("Edit")
            }
        }
    }
}