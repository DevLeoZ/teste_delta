package com.teste.delta.service

import com.teste.delta.dto.LoginRequestDTO
import com.teste.delta.dto.RegisterRequestDTO
import com.teste.delta.entity.User
import com.teste.delta.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthServiceTest {

    private val userRepository: UserRepository = mockk()

    private val passwordEncoder = BCryptPasswordEncoder()

    private val authService = AuthService(
        userRepository,
        passwordEncoder
    )

    @Test
    fun `should register user successfully`() {

        val dto = RegisterRequestDTO(
            name = "Leonardo",
            lastName = "Alves",
            email = "leo@email.com",
            password = "123456"
        )

        every {
            userRepository.findByEmail(dto.email)
        } returns null

        every {
            userRepository.save(any())
        } answers {
            firstArg()
        }

        val result = authService.register(dto)

        assertEquals(dto.email, result.email)

        verify(exactly = 1) {
            userRepository.save(any())
        }
    }

    @Test
    fun `should login user successfully`() {

        val dto = LoginRequestDTO(
            email = "leo@email.com",
            password = "123456"
        )

        val user = User(
            id = 1,
            name = "Leonardo",
            lastName = "Alves",
            email = "leo@email.com",
            password = passwordEncoder.encode("123456")
        )

        every {
            userRepository.findByEmail(dto.email)
        } returns user

        val result = authService.login(dto)

        assertEquals("1", result)
    }
}