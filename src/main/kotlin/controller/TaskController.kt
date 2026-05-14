package com.teste.delta.controller

import com.teste.delta.dto.TaskDTO
import com.teste.delta.entity.Task
import com.teste.delta.entity.User
import com.teste.delta.service.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService
) {

    @PostMapping
    fun create(
        @RequestBody dto: TaskDTO,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<Task> {

        val task = taskService.create(dto.copy(userId = user.id))

        return ResponseEntity.ok(task)
    }

    @GetMapping
    fun findByUser(@AuthenticationPrincipal user: User): ResponseEntity<List<Task>> {
        return ResponseEntity.ok(taskService.findByUserId(user.id))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody dto: TaskDTO,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<Task> {

        val task = taskService.update(id, dto, user.id)

        return ResponseEntity.ok(task)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<Void> {

        taskService.delete(id, user.id)

        return ResponseEntity.noContent().build()
    }
}