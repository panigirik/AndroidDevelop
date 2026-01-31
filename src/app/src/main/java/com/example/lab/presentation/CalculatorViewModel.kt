package com.example.lab.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.lab.domain.CalculatorAction
import com.example.lab.domain.CalculatorOperation
import com.example.lab.domain.CalculatorState

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.value)
            is CalculatorAction.Operation -> enterOperation(action.operation)
            CalculatorAction.Clear -> _state.value = CalculatorState()
            CalculatorAction.Delete -> performDelete()
            CalculatorAction.Calculate -> performCalculation()
            CalculatorAction.Decimal -> enterDecimal()
        }
    }

    private fun enterNumber(number: Int) {
        val current = _state.value
        if (current.operation == null) {
            _state.value = current.copy(number1 = current.number1 + number)
        } else {
            _state.value = current.copy(number2 = current.number2 + number)
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        val current = _state.value
        if (current.number1.isNotEmpty()) {
            _state.value = current.copy(operation = operation)
        }
    }

    private fun performCalculation() {
        val current = _state.value
        val n1 = current.number1.toDoubleOrNull()
        val n2 = current.number2.toDoubleOrNull()
        val op = current.operation

        if (n1 == null || n2 == null || op == null) {
            return
        }

        val result = when (op) {
            CalculatorOperation.Add -> n1 + n2
            CalculatorOperation.Subtract -> n1 - n2
            CalculatorOperation.Multiply -> n1 * n2
            CalculatorOperation.Divide -> n1 / n2
        }

        _state.value = CalculatorState(
            number1 = result.toString(),
            number2 = "",
            operation = null
        )
    }

    private fun performDelete() {
        val current = _state.value
        if (current.number2.isNotEmpty()) {
            _state.value = current.copy(number2 = current.number2.dropLast(1))
        } else if (current.operation != null) {
            _state.value = current.copy(operation = null)
        } else if (current.number1.isNotEmpty()) {
            _state.value = current.copy(number1 = current.number1.dropLast(1))
        }
    }

    private fun enterDecimal() {
        val current = _state.value
        if (current.operation == null && !current.number1.contains(".")) {
            _state.value = current.copy(number1 = current.number1 + ".")
        } else if (
            current.operation != null &&
            !current.number2.contains(".")
        ) {
            _state.value = current.copy(number2 = current.number2 + ".")
        }
    }
}
