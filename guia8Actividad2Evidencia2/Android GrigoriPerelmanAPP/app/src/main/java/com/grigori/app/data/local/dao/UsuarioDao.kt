package com.grigori.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.grigori.app.data.local.entities.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): Usuario?

    @Insert
    suspend fun insert(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios")
    suspend fun getAll(): List<Usuario>
}
