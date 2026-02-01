package com.example.lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.lab.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {

    private val prefs by lazy {
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            this,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CalculatorTheme {
                var isAuthorized by remember {
                    mutableStateOf(prefs.contains("pass_key"))
                }

                if (!isAuthorized) {
                    PassKeyScreen(
                        onSuccess = { isAuthorized = true },
                        onSavePassKey = { key ->
                            prefs.edit().putString("pass_key", key.hashCode().toString()).apply()
                        },
                        savedHash = prefs.getString("pass_key", null)
                    )
                } else {
                    // 👉 СТАРЫЙ ФУНКЦИОНАЛ НЕ ТРОГАЕМ
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PassKeyScreen(
    onSuccess: () -> Unit,
    onSavePassKey: (String) -> Unit,
    savedHash: String?
) {
    var input by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (savedHash == null) "Create Pass Key" else "Enter Pass Key",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Pass Key") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (savedHash == null) {
                    onSavePassKey(input)
                    onSuccess()
                } else {
                    if (input.hashCode().toString() == savedHash) {
                        onSuccess()
                    } else {
                        error = "Incorrect Pass Key"
                    }
                }
            }
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAuth() {
    CalculatorTheme {
        Greeting("Android")
    }
}
