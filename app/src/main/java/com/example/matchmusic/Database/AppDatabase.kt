package com.example.matchmusic.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.matchmusic.Interfaces.GeneroDAO
import com.example.matchmusic.Interfaces.UsuarioDAO
import com.example.matchmusic.Model.Genero
import com.example.matchmusic.Model.Usuario

@Database(
    entities = arrayOf(
        Usuario::class,
        Genero::class
    ),
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao() : UsuarioDAO
    abstract fun generoDao() : GeneroDAO
}