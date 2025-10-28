package com.example.demo.Dto

import com.example.demo.Status

data class TaskResponseDto(
    val title: String,
    val description: String,
    val status: Status,
    val createdAt: String
)
