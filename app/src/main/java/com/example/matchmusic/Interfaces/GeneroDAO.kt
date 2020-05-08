package com.example.matchmusic.Interfaces

import androidx.room.*
import com.example.matchmusic.Model.Genero

@Dao
interface GeneroDAO {
    @Insert fun inserirGenero(genero: Genero)
    @Update fun atualizarGenero(genero: Genero)
    @Delete fun deletarGenero(genero: Genero)

    @Query("SELECT * FROM Genero")
    fun pegarGeneros() : Array<Genero>
    @Query("SELECT * FROM Genero WHERE id = :i")
    fun pegarGenero(i : Int) : Array<Genero>
    @Query("SELECT * FROM Genero WHERE usuario_id = :ui")
    fun pegarGeneroUsuario(ui : Int) : Array<Genero>
}