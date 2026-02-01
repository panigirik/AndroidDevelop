package com.example.lab.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.lab.platform.PassKeyManager

@Composable
fun PassKeyScreen(
    activity: FragmentActivity,
    passKeyManager: PassKeyManager,
    onAuthenticated: () -> Unit
) {
    var input by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var resetMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(if (resetMode) "Set New Pass Key" else "Enter Pass Key")

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            placeholder = { Text("Pass Key") }
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            if (resetMode) {
                if (input.isBlank()) {
                    error = "Pass Key cannot be empty"
                    return@Button
                }
                passKeyManager.resetPassKey(input)
                onAuthenticated()
            }
            else {
                if (passKeyManager.validatePassKey(input)) {
                    onAuthenticated()
                } else {
                    error = "Incorrect Pass Key"
                }
            }
        }) {
            Text(if (resetMode) "Save" else "Submit")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            passKeyManager.authenticateBiometric(
                activity,
                onSuccess = {
                    resetMode = true
                    input = ""
                    error = ""
                },
                onError = { error = it }
            )
        }) {
            Text("Forgot Pass Key")
        }

        if (error.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }
}
