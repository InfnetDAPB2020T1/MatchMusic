package com.example.matchmusic.throws

class UsuarioEmailException : Throwable() {
    override val message: String?
        get() = "O email não é valido"
}
