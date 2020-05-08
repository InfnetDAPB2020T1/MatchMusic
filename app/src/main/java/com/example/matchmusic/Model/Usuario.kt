package com.example.matchmusic.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.matchmusic.throws.*
import java.io.Serializable

@Entity
class Usuario(
    var nome: String,
    var sobrenome: String,
    var pensamento: String? = "Oque está pensando?",
    var sobremim: String? = "Sobre mim.",
    var email: String,
    var senha: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
) : Serializable {
    init {
        if(nome.length < 3) throw UsuarioNomeException()
        if(sobrenome.length < 3) throw UsuarioSobrenomeException()
        if(senha.length < 8) throw UsuarioSenhaException()
        if(senha.length >16 ) throw UsuarioSenhaGrandeException()
        if(!EmailValidator.validarEmail(email)) throw UsuarioEmailException()
    }
}