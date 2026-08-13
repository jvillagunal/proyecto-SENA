package com.grigori.app.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grigori.app.data.local.AppDatabase
import com.grigori.app.data.repository.UsuarioRepository
import com.grigori.app.utils.SessionManager
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UsuarioRepository(AppDatabase.getInstance(application).usuarioDao())
    private val _profileName = MutableLiveData<String>()
    val profileName: LiveData<String> = _profileName
    private val _profileEmail = MutableLiveData<String>()
    val profileEmail: LiveData<String> = _profileEmail
    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> = _updateResult

    fun loadProfile() {
        viewModelScope.launch {
            if (SessionManager.correo.isNotBlank()) {
                val usuario = repository.getUserByEmail(SessionManager.correo)
                usuario?.let {
                    _profileName.value = it.nombre
                    _profileEmail.value = it.email
                }
            }
        }
    }

    fun updateName(nombre: String) {
        viewModelScope.launch {
            val current = repository.getUserByEmail(SessionManager.correo)
            if (current != null) {
                val actual = current.copy(nombre = nombre.trim())
                repository.insert(actual)
                SessionManager.nombre = actual.nombre
                _updateResult.value = true
            } else {
                _updateResult.value = false
            }
        }
    }
}
