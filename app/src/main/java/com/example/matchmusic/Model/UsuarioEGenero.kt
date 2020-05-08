package com.example.matchmusic.Model

import androidx.room.Embedded
import androidx.room.Relation

class UsuarioEGenero(
    @Embedded val usuario: Usuario,
    @Relation(
        parentColumn = "id", //ID de Usuario
        entityColumn = "usuario_id"
    ) var genero: Genero
) {
}