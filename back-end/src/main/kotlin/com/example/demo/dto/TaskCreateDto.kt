package com.example.demo.dto

import com.example.demo.utils.Status

data class TaskCreateDto(
    val title: String,
    val description: String,
    val status: Status
)