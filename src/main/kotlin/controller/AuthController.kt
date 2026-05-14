package com.teste.delta.controller

import com.teste.delta.dto.LoginRequestDTO
import com.teste.delta.dto.RegisterRequestDTO
import com.teste.delta.entity.User
import com.teste.delta.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody dto: RegisterRequestDTO
    ): ResponseEntity<User> {

        val user = authService.register(dto)

        return ResponseEntity.ok(user)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody dto: LoginRequestDTO
    ): ResponseEntity<Map<String, String>> {

        val token = authService.login(dto)

        return ResponseEntity.ok(mapOf("token" to token))
    }
}