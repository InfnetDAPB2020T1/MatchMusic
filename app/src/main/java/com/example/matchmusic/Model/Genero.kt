package com.example.matchmusic.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Genero(
    var tipo: String,
    var usuario_id: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
) : Serializable {
}