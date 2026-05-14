package com.teste.delta.dto

data class RegisterRequestDTO(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)