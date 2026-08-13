package com.grigori.app.ui.newproblem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grigori.app.data.local.AppDatabase
import com.grigori.app.data.local.entities.Problema
import com.grigori.app.data.repository.ProblemaRepository
import com.grigori.app.utils.SessionManager
import kotlinx.coroutines.launch

class NewProblemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProblemaRepository(AppDatabase.getInstance(application).problemaDao())
    private val _saveResult = MutableLiveData<Long>()
    val saveResult: LiveData<Long> = _saveResult

    fun saveProblem(ecuacion: String) {
        viewModelScope.launch {
            val steps = buildSolutionSteps(ecuacion)
            val result = evaluateExpression(ecuacion)
            val problema = Problema(
                usuarioId = SessionManager.usuarioId,
                ecuacion = ecuacion.trim(),
                resultado = result,
                pasos = steps
            )
            val id = repository.insert(problema)
            _saveResult.value = id
        }
    }

    private fun evaluateExpression(expression: String): String {
        return try {
            val parser = ExpressionParser(expression)
            parser.evaluate().toString()
        } catch (ex: Exception) {
            "Error"
        }
    }

    private fun buildSolutionSteps(expression: String): String {
        return try {
            val parser = ExpressionParser(expression)
            parser.steps()
        } catch (ex: Exception) {
            "No se pudo resolver la ecuación."
        }
    }
}

private class ExpressionParser(private val value: String) {
    fun evaluate(): Double {
        val tokens = tokenize(value)
        val values = mutableListOf<Double>()
        val ops = mutableListOf<Char>()

        fun applyOp() {
            val right = values.removeLast()
            val left = values.removeLast()
            when (ops.removeLast()) {
                '+' -> values.add(left + right)
                '-' -> values.add(left - right)
                '*' -> values.add(left * right)
                '/' -> values.add(left / right)
                else -> throw IllegalArgumentException("Operador no válido")
            }
        }

        for (token in tokens) {
            when {
                token.isNumber() -> values.add(token.toDouble())
                token.isOperator() -> {
                    while (ops.isNotEmpty() && hasPrecedence(token[0], ops.last())) {
                        applyOp()
                    }
                    ops.add(token[0])
                }
                token == "(" -> ops.add('(')
                token == ")" -> {
                    while (ops.isNotEmpty() && ops.last() != '(') {
                        applyOp()
                    }
                    if (ops.isNotEmpty() && ops.last() == '(') ops.removeLast()
                }
                else -> throw IllegalArgumentException("Token inválido: $token")
            }
        }
        while (ops.isNotEmpty()) applyOp()
        return values.lastOrNull() ?: 0.0
    }

    fun steps(): String {
        val result = evaluate()
        return buildString {
            append("Ecuación: ${value.trim()}\n")
            append("1. Interpretar la expresión matemática.\n")
            append("2. Aplicar operaciones en el orden correcto: paréntesis, multiplicación, división, suma y resta.\n")
            append("3. Resultado calculado: $result\n")
            append("4. Guardar el problema en el historial.")
        }
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false
        return true
    }

    private fun tokenize(input: String): List<String> {
        val cleaned = input.replace(" ", "")
        val tokens = mutableListOf<String>()
        val number = StringBuilder()
        for (char in cleaned) {
            when {
                char.isDigit() || char == '.' -> number.append(char)
                char.isOperator() || char == '(' || char == ')' -> {
                    if (number.isNotEmpty()) {
                        tokens.add(number.toString())
                        number.clear()
                    }
                    tokens.add(char.toString())
                }
                else -> throw IllegalArgumentException("Carácter no permitido: $char")
            }
        }
        if (number.isNotEmpty()) tokens.add(number.toString())
        return tokens
    }

    private fun String.isNumber() = this.toDoubleOrNull() != null
    private fun String.isOperator() = length == 1 && this[0] in listOf('+', '-', '*', '/')
}
