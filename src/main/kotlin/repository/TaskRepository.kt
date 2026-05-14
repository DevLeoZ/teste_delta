package com.teste.delta.repository

import com.teste.delta.entity.Task
import com.teste.delta.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {

    fun findByUserId(userId: Long): List<Task>
}
