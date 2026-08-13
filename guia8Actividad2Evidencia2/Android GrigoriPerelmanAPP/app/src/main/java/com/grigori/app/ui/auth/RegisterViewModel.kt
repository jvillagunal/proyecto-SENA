package com.grigori.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grigori.app.data.local.entities.Usuario
import com.grigori.app.data.repository.UsuarioRepository
import com.grigori.app.utils.SessionManager
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UsuarioRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    fun register(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            val existing = repository.getUserByEmail(email.trim())
            if (existing == null) {
                val id = repository.insert(
                    Usuario(
                        nombre = nombre.trim(),
                        email = email.trim(),
                        passwordHash = password.trim()
                    )
                )
                if (id > 0) {
                    SessionManager.usuarioId = id.toInt()
                    SessionManager.correo = email.trim()
                    SessionManager.nombre = nombre.trim()
                    _registerResult.value = true
                    return@launch
                }
            }
            _registerResult.value = false
        }
    }
}
