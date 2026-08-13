package com.grigori.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grigori.app.data.local.entities.Usuario
import com.grigori.app.data.repository.UsuarioRepository
import com.grigori.app.utils.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult
    private val _loginUser = MutableLiveData<Usuario?>()
    val loginUser: LiveData<Usuario?> = _loginUser

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val usuario = repository.getUserByEmail(email.trim())
            val success = usuario != null && usuario.passwordHash == password.trim()
            if (success) {
                usuario?.let {
                    SessionManager.usuarioId = it.id
                    SessionManager.correo = it.email
                    SessionManager.nombre = it.nombre
                }
                _loginUser.value = usuario
            }
            _loginResult.value = success
        }
    }
}
