package com.example.demo.model

import com.example.demo.utils.Status
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("TASKS")
data class Task(
    @Id
    val id: Long? = null,
    var title: String,
    var description: String? = null,
    var status: Status = Status.TODO,
    val createdAt: Instant = Instant.now()
)
