package com.example.demo.controller

import com.example.demo.dto.TaskCreateDto
import com.example.demo.model.Task
import com.example.demo.service.TaskService
import com.example.demo.utils.SortOrder
import com.example.demo.utils.TaskSortField
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    @GetMapping
    fun getTasks(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) sortField: TaskSortField?,
        @RequestParam(required = false, defaultValue = "ASC") sortOrder: SortOrder
    ): ResponseEntity<List<Task>> {
        val tasks = taskService.findTaskByFilters(title, status, sortField, sortOrder)
        return ResponseEntity.ok(tasks)
    }

    @PostMapping
    fun createTask(@RequestBody taskDto: TaskCreateDto): ResponseEntity<Task> {
        val task = taskService.addTask(taskDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(task)
    }

    @PutMapping("/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @RequestBody taskDto: TaskCreateDto
    ): ResponseEntity<Task> {
        val updatedTask = taskService.updateTask(id, taskDto)
        return if (updatedTask != null) {
            ResponseEntity.ok(updatedTask)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Unit> {
        taskService.deleteTask(id)
        return ResponseEntity.noContent().build()
    }
}
