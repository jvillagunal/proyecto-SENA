package com.grigori.app.ui.dashboard

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

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val problemaRepository = ProblemaRepository(AppDatabase.getInstance(application).problemaDao())
    private val _greeting = MutableLiveData<String>()
    val greeting: LiveData<String> = _greeting
    private val _recentProblems = MutableLiveData<List<Problema>>()
    val recentProblems: LiveData<List<Problema>> = _recentProblems

    fun loadDashboard() {
        viewModelScope.launch {
            val nombre = SessionManager.nombre.ifBlank { "Estudiante" }
            _greeting.value = "Bienvenido, $nombre"
            if (SessionManager.usuarioId > 0) {
                _recentProblems.value = problemaRepository.getProblemsForUser(SessionManager.usuarioId).take(3)
            } else {
                _recentProblems.value = emptyList()
            }
        }
    }
}
