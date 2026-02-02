package com.example.lab

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab.data.history.HistoryRepository
import com.example.lab.data.theme.FirebaseThemeRepository
import com.example.lab.presentation.CalculatorScreen
import com.example.lab.presentation.CalculatorViewModel
import com.example.lab.presentation.PassKeyScreen
import com.example.lab.ui.theme.CalculatorTheme
import com.example.lab.platform.PassKeyManager
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {

    private val historyRepository = HistoryRepository()

    private val viewModel by viewModels<CalculatorViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CalculatorViewModel(historyRepository) as T
            }
        }
    }

    private val passKeyManager by lazy { PassKeyManager(this) }
    private val themeRepository by lazy { FirebaseThemeRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()

        setContent {
            var isAuthorized by remember { mutableStateOf(false) }

            var darkTheme by remember { mutableStateOf(false) }
            var primaryColor by remember { mutableStateOf(Color(0xFF6750A4)) }

            var isThemeLoaded by remember { mutableStateOf(false) }

            val scope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                isAuthorized = passKeyManager.isPassKeySet().not()

                try {
                    val cloudTheme = themeRepository.fetchTheme()
                    darkTheme = cloudTheme.darkTheme
                    primaryColor = Color(
                        android.graphics.Color.parseColor(cloudTheme.primaryColor)
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isThemeLoaded = true
                }
            }

            if (!isThemeLoaded) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {}
                return@setContent
            }

            CalculatorTheme(
                darkTheme = darkTheme,
                primaryColor = primaryColor
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {

                        if (!isAuthorized) {
                            PassKeyScreen(
                                activity = this@MainActivity,
                                passKeyManager = passKeyManager,
                                onAuthenticated = { isAuthorized = true }
                            )
                        } else {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = if (darkTheme) "DARK" else "LIGHT",
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Text(
                                        text = "Dark theme",
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }

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
}
