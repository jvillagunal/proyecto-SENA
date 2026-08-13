package com.grigori.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "problemas")
data class Problema(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val ecuacion: String,
    val resultado: String,
    val pasos: String,
    val fechaCreacion: Long = System.currentTimeMillis()
)
