package com.example.zadaca_1

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
import androidx.compose.ui.unit.dp
import com.example.zadaca_1.ui.theme.Zadaca_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Zadaca_1Theme {
                Zadaca1Screen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Zadaca1Screen() {

    var searchQuery by remember { mutableStateOf("") }
    var newTag by remember { mutableStateOf("") }

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
                title = { Text("Example for zadaca 1 - INKI943") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Enter Twitter search query here") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tag input + Save
            Row(verticalAlignment = Alignment.CenterVertically) {

                OutlinedTextField(
                    value = newTag,
                    onValueChange = { newTag = it },
                    placeholder = { Text("Tag your query") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (newTag.isNotBlank()) {
                            tags.add(newTag)
                            newTag = ""
                        }
                    }
                ) {
                    Text("Save")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tagged Searches",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tag list
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(tags) { tag ->
                    TagItem(tag)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Clear button
            Button(
                onClick = { tags.clear() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Tags")
            }
        }
    }
}

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