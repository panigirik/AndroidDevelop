package com.example.lab.domain

sealed class CalculatorOperation {
    object Add : CalculatorOperation()
    object Subtract : CalculatorOperation()
    object Multiply : CalculatorOperation()
    object Divide : CalculatorOperation()
}