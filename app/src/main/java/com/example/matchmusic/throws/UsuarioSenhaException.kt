package com.example.matchmusic.throws

class UsuarioSenhaException : Throwable() {
    override val message: String?
        get() = "Senha deve ter mais que oito caracteres."
}