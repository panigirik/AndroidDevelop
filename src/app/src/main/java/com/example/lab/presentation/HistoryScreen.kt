package com.example.lab.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab.data.history.HistoryRepository
import com.example.lab.domain.HistoryItem

@Composable
fun HistoryScreen(
    historyRepository: HistoryRepository,
    onBack: () -> Unit
) {
    var history by remember { mutableStateOf<List<HistoryItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        historyRepository.load {
            history = it
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        Button(onClick = onBack) {
            Text("Back")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(history) { item ->
                Text("${item.expression} = ${item.result}")
                Divider()
            }
        }
    }
}