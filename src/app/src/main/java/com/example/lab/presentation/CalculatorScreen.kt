package com.example.lab.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lab.domain.CalculatorAction
import com.example.lab.domain.CalculatorOperation
import com.example.lab.platform.performButtonFeedback

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {

    val state by viewModel.state.collectAsState()

    // 🔹 Platform API access
    val context = LocalContext.current
    val view = LocalView.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = state.number1 +
                    (state.operation?.let { " ${symbol(it)} " } ?: "") +
                    state.number2,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Row {
                CalcButton("7") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(7))
                }
                CalcButton("8") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(8))
                }
                CalcButton("9") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(9))
                }
                CalcButton("÷") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(
                        CalculatorAction.Operation(CalculatorOperation.Divide)
                    )
                }
            }

            Row {
                CalcButton("4") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(4))
                }
                CalcButton("5") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(5))
                }
                CalcButton("6") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(6))
                }
                CalcButton("×") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(
                        CalculatorAction.Operation(CalculatorOperation.Multiply)
                    )
                }
            }

            Row {
                CalcButton("1") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(1))
                }
                CalcButton("2") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(2))
                }
                CalcButton("3") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(3))
                }
                CalcButton("-") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(
                        CalculatorAction.Operation(CalculatorOperation.Subtract)
                    )
                }
            }

            Row {
                CalcButton("0") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Number(0))
                }
                CalcButton(".") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Decimal)
                }
                CalcButton("=") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(CalculatorAction.Calculate)
                }
                CalcButton("+") {
                    performButtonFeedback(context, view)
                    viewModel.onAction(
                        CalculatorAction.Operation(CalculatorOperation.Add)
                    )
                }
            }
        }
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
            .size(80.dp)
    ) {
        Text(text)
    }
}

private fun symbol(operation: CalculatorOperation): String =
    when (operation) {
        CalculatorOperation.Add -> "+"
        CalculatorOperation.Subtract -> "-"
        CalculatorOperation.Multiply -> "×"
        CalculatorOperation.Divide -> "÷"
    }
