package com.grigori.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.grigori.app.data.local.entities.Problema

@Dao
interface ProblemaDao {
    @Query("SELECT * FROM problemas WHERE usuarioId = :usuarioId ORDER BY fechaCreacion DESC")
    suspend fun getProblemsByUsuario(usuarioId: Int): List<Problema>

    @Query("SELECT * FROM problemas WHERE id = :id LIMIT 1")
    suspend fun getProblemById(id: Int): Problema?

    @Insert
    suspend fun insert(problema: Problema): Long

    @Delete
    suspend fun delete(problema: Problema)
}
