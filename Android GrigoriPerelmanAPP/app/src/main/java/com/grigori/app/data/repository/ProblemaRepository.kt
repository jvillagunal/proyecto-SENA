package com.grigori.app.data.repository

import com.grigori.app.data.local.dao.ProblemaDao
import com.grigori.app.data.local.entities.Problema

class ProblemaRepository(private val dao: ProblemaDao) {
    suspend fun getProblemsForUser(usuarioId: Int): List<Problema> = dao.getProblemsByUsuario(usuarioId)
    suspend fun getProblemById(id: Int): Problema? = dao.getProblemById(id)
    suspend fun insert(problema: Problema): Long = dao.insert(problema)
    suspend fun delete(problema: Problema) = dao.delete(problema)
}
