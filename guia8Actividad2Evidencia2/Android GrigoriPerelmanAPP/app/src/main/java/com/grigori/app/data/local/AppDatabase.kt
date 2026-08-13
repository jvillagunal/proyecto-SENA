package com.grigori.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grigori.app.data.local.dao.ProblemaDao
import com.grigori.app.data.local.dao.UsuarioDao
import com.grigori.app.data.local.entities.Problema
import com.grigori.app.data.local.entities.Usuario

@Database(entities = [Usuario::class, Problema::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun problemaDao(): ProblemaDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val created = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "grigori_db"
                ).fallbackToDestructiveMigration().build()
                instance = created
                created
            }
        }
    }
}
