package com.example.matchmusic.Interfaces

import androidx.room.*
import com.example.matchmusic.Model.Usuario
import com.example.matchmusic.Model.UsuarioEGenero

@Dao
interface UsuarioDAO {
    @Insert fun inserirUsuario(usuario: Usuario)
    @Update fun atualizarUsuario(usuario: Usuario)
    @Delete fun deletarUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    fun pegarUsuarios() : Array<Usuario>
    @Query("SELECT * FROM Usuario WHERE id = :i")
    fun pegarUsuario(i : Int) : Array<Usuario>
    @Query("SELECT * FROM Usuario WHERE email = :email AND senha = :senha")
    fun validarUsuario(email : String, senha : String) : Array<Usuario>


    @Transaction
    @Query("SELECT * FROM Usuario")
    fun pegarUsuarioseGeneros() : Array<UsuarioEGenero>
    @Transaction
    @Query("SELECT * FROM Usuario WHERE id = :i")
    fun pegarUsuarioeGeneros(i: Int?) : Array<UsuarioEGenero>
}