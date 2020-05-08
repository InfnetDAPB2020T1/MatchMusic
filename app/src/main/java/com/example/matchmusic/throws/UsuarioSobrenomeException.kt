package com.example.matchmusic.throws

class UsuarioSobrenomeException : Throwable() {
    override val message: String?
        get() = "Sobrenome deve ter mais que dois caracteres."
}
