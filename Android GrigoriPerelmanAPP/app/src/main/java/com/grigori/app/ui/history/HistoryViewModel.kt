package com.grigori.app.ui.history

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

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProblemaRepository(AppDatabase.getInstance(application).problemaDao())
    private val _history = MutableLiveData<List<Problema>>()
    val history: LiveData<List<Problema>> = _history
    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean> = _deleteResult

    fun loadHistory() {
        viewModelScope.launch {
            if (SessionManager.usuarioId > 0) {
                _history.value = repository.getProblemsForUser(SessionManager.usuarioId)
            }
        }
    }

    fun deleteProblem(problem: Problema) {
        viewModelScope.launch {
            repository.delete(problem)
            _deleteResult.value = true
            loadHistory()
        }
    }
}
