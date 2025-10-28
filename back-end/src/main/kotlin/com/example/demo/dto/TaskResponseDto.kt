package com.example.demo.dto

import com.example.demo.utils.Status

data class TaskResponseDto(
    val title: String,
    val description: String,
    val status: Status,
    val createdAt: String
)
