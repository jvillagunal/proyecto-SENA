package com.grigori.app.ui.solution

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.grigori.app.data.local.AppDatabase
import com.grigori.app.data.local.entities.Problema
import com.grigori.app.data.repository.ProblemaRepository
import kotlinx.coroutines.launch

class SolutionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ProblemaRepository(AppDatabase.getInstance(application).problemaDao())
    private val _problem = MutableLiveData<Problema?>()
    val problem: LiveData<Problema?> = _problem

    fun loadProblem(problemId: Int) {
        viewModelScope.launch {
            _problem.value = repository.getProblemById(problemId)
        }
    }
}
