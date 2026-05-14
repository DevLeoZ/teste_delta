package com.teste.delta.service

import com.teste.delta.dto.LoginRequestDTO
import com.teste.delta.dto.RegisterRequestDTO
import com.teste.delta.entity.User
import com.teste.delta.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(dto: RegisterRequestDTO): User {

        val exists = userRepository.findByEmail(dto.email)
        if (exists != null) {
            throw RuntimeException("Email já cadastrado")
        }

        val user = User(
            name = dto.name,
            lastName = dto.lastName,
            email = dto.email,
            password = passwordEncoder.encode(dto.password)
        )

        return userRepository.save(user)
    }

    fun login(dto: LoginRequestDTO): String {

        val user = userRepository.findByEmail(dto.email)
            ?: throw RuntimeException("Usuário não encontrado")

        if (!passwordEncoder.matches(dto.password, user.password)) {
            throw RuntimeException("Senha inválida")
        }

        return user.id.toString()
    }
}