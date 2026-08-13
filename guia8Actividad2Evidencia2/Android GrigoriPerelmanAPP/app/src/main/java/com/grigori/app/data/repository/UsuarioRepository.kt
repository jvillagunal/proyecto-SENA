package com.grigori.app.data.repository

import com.grigori.app.data.local.dao.UsuarioDao
import com.grigori.app.data.local.entities.Usuario

open class UsuarioRepository(private val dao: UsuarioDao?) {
    open suspend fun getUserByEmail(email: String): Usuario? = dao?.getUserByEmail(email)
    open suspend fun insert(usuario: Usuario): Long = dao?.insert(usuario) ?: -1
    open suspend fun getAll(): List<Usuario> = dao?.getAll() ?: emptyList()
}
