package com.example.demo

import com.example.demo.utils.Status
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("tasks")
data class Task(
    @Id
    val id: Long? = null,
    var title: String,
    var description: String,
    var status: Status,
    val createdAt: Instant = Instant.now()
)
