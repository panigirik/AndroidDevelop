package com.example.lab.domain

sealed class CalculatorAction {
    data class Number(val value: Int) : CalculatorAction()
    object Clear : CalculatorAction()
    object Delete : CalculatorAction()
    object Calculate : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction()
    object Decimal : CalculatorAction()
}