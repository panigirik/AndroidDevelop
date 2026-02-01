package com.example.lab.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter Pass Key")

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            placeholder = { Text("Pass Key") }
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            if (passKeyManager.validatePassKey(input)) {
                onAuthenticated()
            } else {
                error = "Incorrect Pass Key"
            }
        }) {
            Text("Submit")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            passKeyManager.authenticateBiometric(
                activity,
                onSuccess = onAuthenticated,
                onError = { error = it }
            )
        }) {
            Text("Use Biometric")
        }

        if (error.isNotEmpty()) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }
}