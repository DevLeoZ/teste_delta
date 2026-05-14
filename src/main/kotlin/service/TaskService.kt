package com.teste.delta.service


import com.teste.delta.dto.TaskDTO
import com.teste.delta.entity.Task
import com.teste.delta.repository.TaskRepository
import com.teste.delta.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository
) {

    fun create(dto: TaskDTO): Task {

        val user = userRepository.findById(dto.userId)
            .orElseThrow {
                RuntimeException("Usuário não encontrado")
            }

        val task = Task(
            title = dto.title,
            completed = dto.completed,
            user = user
        )

        return taskRepository.save(task)
    }

    fun findByUserId(userId: Long): List<Task> {
        return taskRepository.findByUserId(userId)
    }

    fun update(id: Long, dto: TaskDTO, userId: Long): Task {

        val task = taskRepository.findById(id)
            .orElseThrow {
                RuntimeException("Tarefa não encontrada")
            }

        if (task.user.id != userId) {
            throw RuntimeException("Acesso negado")
        }

        val updatedTask = task.copy(
            title = dto.title,
            completed = dto.completed
        )

        return taskRepository.save(updatedTask)
    }

    fun delete(id: Long, userId: Long) {

        val task = taskRepository.findById(id)
            .orElseThrow {
                RuntimeException("Tarefa não encontrada")
            }

        if (task.user.id != userId) {
            throw RuntimeException("Acesso negado")
        }

        taskRepository.delete(task)
    }
}