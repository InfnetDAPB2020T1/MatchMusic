package com.example.matchmusic.throws

class UsuarioNomeException : Throwable() {
    override val message: String?
        get() = "Nome deve ter mais que dois caracteres."
}
