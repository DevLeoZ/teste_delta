package com.teste.delta.controller

import com.teste.delta.dto.TaskDTO
import com.teste.delta.entity.Task
import com.teste.delta.entity.User
import com.teste.delta.service.TaskService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(TaskController::class)
class TaskControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @TestConfiguration
    class TestConfig {
        @Bean
        fun taskService(): TaskService = mockk()
    }

    @Test
    @WithMockUser
    fun `should create task successfully`() {
        val dto = TaskDTO(1, "Test Task", false)
        val user = User(1, "Leo", "Alves", "leo@email.com", "pass")
        val task = Task(1, "Test Task", false, user)

        every { any<TaskService>().create(dto.copy(userId = 1)) } returns task

        mockMvc.post("/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isOk() }
            jsonPath("$.title") { value("Test Task") }
        }
    }

    @Test
    @WithMockUser
    fun `should get tasks by user`() {
        val user = User(1, "Leo", "Alves", "leo@email.com", "pass")
        val task = Task(1, "Test Task", false, user)

        every { any<TaskService>().findByUserId(1) } returns listOf(task)

        mockMvc.get("/tasks").andExpect {
            status { isOk() }
            jsonPath("$[0].title") { value("Test Task") }
        }
    }
}