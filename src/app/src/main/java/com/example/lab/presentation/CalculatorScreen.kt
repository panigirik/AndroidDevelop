package com.example.lab.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lab.domain.CalculatorAction
import com.example.lab.domain.CalculatorOperation
import com.example.lab.platform.performHapticFeedback
import com.example.lab.platform.playEqualsSound

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = state.number1 +
                    (state.operation?.let { " ${symbol(it)} " } ?: "") +
                    state.number2,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.End
        )

        Column {

            Row {
                CalcButton("C") {
                    performHapticFeedback(context)
                    viewModel.onAction(CalculatorAction.Clear)
                }
            }

            Spacer(Modifier.height(8.dp))

            row(viewModel, "7", "8", "9", "÷") {
                CalculatorAction.Operation(CalculatorOperation.Divide)
            }

            row(viewModel, "4", "5", "6", "×") {
                CalculatorAction.Operation(CalculatorOperation.Multiply)
            }

            row(viewModel, "1", "2", "3", "-") {
                CalculatorAction.Operation(CalculatorOperation.Subtract)
            }

            Row {
                numberButton("0", context, viewModel)
                CalcButton(".") {
                    performHapticFeedback(context)
                    viewModel.onAction(CalculatorAction.Decimal)
                }
                CalcButton("=") {
                    performHapticFeedback(context)
                    playEqualsSound()
                    viewModel.onAction(CalculatorAction.Calculate)
                }
                CalcButton("+") {
                    performHapticFeedback(context)
                    viewModel.onAction(
                        CalculatorAction.Operation(CalculatorOperation.Add)
                    )
                }
            }
        }
    }
}

@Composable
private fun row(
    viewModel: CalculatorViewModel,
    n1: String,
    n2: String,
    n3: String,
    op: String,
    action: () -> CalculatorAction
) {
    val context = LocalContext.current

    Row {
        numberButton(n1, context, viewModel)
        numberButton(n2, context, viewModel)
        numberButton(n3, context, viewModel)
        CalcButton(op) {
            performHapticFeedback(context)
            viewModel.onAction(action())
        }
    }
}

@Composable
private fun numberButton(
    text: String,
    context: android.content.Context,
    viewModel: CalculatorViewModel
) {
    CalcButton(text) {
        performHapticFeedback(context)
        viewModel.onAction(CalculatorAction.Number(text.toInt()))
    }
}

@Composable
private fun CalcButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .size(76.dp)
    ) {
        Text(text)
    }
}

fun symbol(operation: CalculatorOperation): String =
    when (operation) {
        CalculatorOperation.Add -> "+"
        CalculatorOperation.Subtract -> "-"
        CalculatorOperation.Multiply -> "×"
        CalculatorOperation.Divide -> "÷"
    }
