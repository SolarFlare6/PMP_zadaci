package com.example.zadaca4

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.zadaca4.ui.theme.Zadaca4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Zadaca4Theme {
                DictionaryScreen()
            }
        }
    }
}

fun loadDictionary(context: Context): Map<String, String> {
    val dictionary = mutableMapOf<String, String>()
    val lines = context.assets.open("dictionary.txt").bufferedReader().readLines()

    for (line in lines) {
        val parts = line.split(",")
        if (parts.size == 2) {
            val en = parts[0].trim().lowercase()
            val mk = parts[1].trim().lowercase()
            dictionary[en] = mk
            dictionary[mk] = en
        }
    }
    return dictionary
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryScreen() {

    val context = LocalContext.current
    val dictionary = remember { loadDictionary(context) }

    var searchQuery by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dictionary") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Enter word") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    val search = searchQuery.lowercase()
                    result = dictionary[search] ?: "Not found"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Result: $result",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "All Words",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(dictionary.entries.toList()) { entry ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "${entry.key} → ${entry.value}",
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}