package com.teste.delta.service

import com.teste.delta.dto.TaskDTO
import com.teste.delta.entity.User
import com.teste.delta.repository.TaskRepository
import com.teste.delta.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class TaskServiceTest {

    private val taskRepository = mockk<TaskRepository>()
    private val userRepository = mockk<UserRepository>()

    private val taskService = TaskService(
        taskRepository,
        userRepository
    )

    @Test
    fun `should create task successfully`() {

        val user = User(
            id = 1,
            name = "Leonardo",
            lastName = "Alves",
            email = "leo@email.com",
            password = "123456"
        )

        val dto = TaskDTO(
            title = "Estudar Kotlin",
            completed = false,
            userId = 1
        )

        every {
            userRepository.findById(1)
        } returns Optional.of(user)

        every {
            taskRepository.save(any())
        } answers {
            firstArg()
        }

        val result = taskService.create(dto)

        assertEquals(dto.title, result.title)
    }
}