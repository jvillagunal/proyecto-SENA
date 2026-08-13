package com.grigori.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val email: String,
    val passwordHash: String,
    val rol: String = "estudiante",
    val fechaRegistro: Long = System.currentTimeMillis()
)
