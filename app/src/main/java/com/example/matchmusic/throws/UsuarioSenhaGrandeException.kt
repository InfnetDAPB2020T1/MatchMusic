package com.example.matchmusic.throws

class UsuarioSenhaGrandeException : Throwable() {
    override val message: String?
        get() = "Senha deve ter menos que dezesseis caracteres."
}