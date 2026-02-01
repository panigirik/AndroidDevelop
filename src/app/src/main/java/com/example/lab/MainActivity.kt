package com.example.lab

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab.data.history.HistoryRepository
import com.example.lab.platform.PassKeyManager
import com.example.lab.presentation.CalculatorScreen
import com.example.lab.presentation.CalculatorViewModel
import com.example.lab.presentation.PassKeyScreen
import com.example.lab.ui.theme.LabTheme
import com.google.firebase.FirebaseApp

class MainActivity : FragmentActivity() {

    private val historyRepository = HistoryRepository()

    private val viewModel by viewModels<CalculatorViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CalculatorViewModel(historyRepository) as T
            }
        }
    }

    private val passKeyManager by lazy { PassKeyManager(this) }

    private var darkTheme by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        setContent {
            var isAuthorized by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                isAuthorized = passKeyManager.isPassKeySet().not()
            }


            LabTheme(darkTheme = darkTheme) {
                Column(modifier = Modifier.fillMaxSize()) {

                    if (!isAuthorized) {
                        PassKeyScreen(
                            activity = this@MainActivity,
                            passKeyManager = passKeyManager,
                            onAuthenticated = { isAuthorized = true }
                        )
                    } else {
                        // переключатель темы
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Dark theme")
                            Spacer(Modifier.width(8.dp))
                            Switch(
                                checked = darkTheme,
                                onCheckedChange = { darkTheme = it }
                            )
                        }

                        CalculatorScreen(viewModel)
                    }
                }
            }
        }
    }
}
