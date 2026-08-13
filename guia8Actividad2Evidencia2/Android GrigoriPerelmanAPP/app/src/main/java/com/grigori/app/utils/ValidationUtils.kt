package com.grigori.app.utils

import android.util.Patterns

object ValidationUtils {
    fun isValidEmail(email: String): Boolean = email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isValidPassword(password: String): Boolean = password.length >= 6
    fun isNotEmpty(value: String): Boolean = value.isNotBlank()
    fun isValidEquation(equation: String): Boolean = equation.isNotBlank() && equation.any { it.isDigit() || it == '+' || it == '-' || it == '*' || it == '/' }
}
