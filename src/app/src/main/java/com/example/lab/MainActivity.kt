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
import com.example.lab.platform.PassKeyManager
import com.example.lab.presentation.CalculatorScreen
import com.example.lab.presentation.CalculatorViewModel
import com.example.lab.presentation.PassKeyScreen
import com.example.lab.ui.theme.LabTheme

class MainActivity : FragmentActivity() {

    private val viewModel by viewModels<CalculatorViewModel>()

    private val passKeyManager by lazy {
        PassKeyManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            var isAuthorized by remember {
                mutableStateOf(passKeyManager.isPassKeySet())
            }

            var darkTheme by remember { mutableStateOf(false) }

            LabTheme(darkTheme = darkTheme) {

                if (!isAuthorized) {
                    PassKeyScreen(
                        activity = this,
                        passKeyManager = passKeyManager,
                        onAuthenticated = {
                            isAuthorized = true
                        }
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

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
