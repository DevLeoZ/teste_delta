package com.teste.delta.controller

import com.teste.delta.dto.LoginRequestDTO
import com.teste.delta.dto.RegisterRequestDTO
import com.teste.delta.entity.User
import com.teste.delta.service.AuthService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(AuthController::class)
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @TestConfiguration
    class TestConfig {
        @Bean
        fun authService(): AuthService = mockk()

        @Bean
        fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
    }

    @Test
    fun `should register user successfully`() {
        val dto = RegisterRequestDTO("Leo", "Alves", "leo@email.com", "123456")
        val user = User(1, "Leo", "Alves", "leo@email.com", "encoded")

        every { any<AuthService>().register(dto) } returns user

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
            jsonPath("$.email") { value("leo@email.com") }
        }
    }

    @Test
    fun `should login user successfully`() {
        val dto = LoginRequestDTO("leo@email.com", "123456")
        val token = "jwt-token"

        every { any<AuthService>().login(dto) } returns token

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
            jsonPath("$.token") { value("jwt-token") }
        }
    }
}