package com.teste.delta.dto

data class TaskDTO(
    val userId: Long,
    val title: String,
    val completed: Boolean = false,
)