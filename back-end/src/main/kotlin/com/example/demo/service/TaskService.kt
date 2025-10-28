package com.example.demo.service

import com.example.demo.dto.TaskCreateDto
import com.example.demo.exception.TaskNotFoundException
import com.example.demo.model.Task
import com.example.demo.repository.TaskRepository
import com.example.demo.utils.SortOrder
import com.example.demo.utils.TaskSortField
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepository: TaskRepository) {

    fun findTaskByFilters(
        title: String?,
        status: String?,
        sortField: TaskSortField? = null,
        sortOrder: SortOrder = SortOrder.ASC
    ): List<Task> {
        val tasks = taskRepository.findAll().filter { task ->
            (title == null || task.title.contains(title, ignoreCase = true)) &&
                    (status == null || task.status.name.equals(status, ignoreCase = true))
        }

        return sortField?.let { field ->
            val comparator = when (field) {
                TaskSortField.TITLE -> compareBy<Task> { it.title.lowercase() }
                TaskSortField.CREATED_AT -> compareBy { it.createdAt }
                TaskSortField.STATUS -> compareBy { it.status.name }
            }

            if (sortOrder == SortOrder.DESC) tasks.sortedWith(comparator.reversed())
            else tasks.sortedWith(comparator)
        } ?: tasks
    }


    fun addTask(taskdto: TaskCreateDto) =
        taskdto.let { dto ->
            Task(
                title = dto.title,
                description = dto.description,
                status = dto.status
            )
        }.also { task ->
            taskRepository.save(task)
        }

    fun updateTask(id: Long, taskdto: TaskCreateDto): Task? {
        val existingTask = taskRepository.findById(id).orElseThrow { TaskNotFoundException(id) }
        return if (existingTask != null) {
            val updatedTask = existingTask.copy(
                title = taskdto.title,
                description = taskdto.description,
                status = taskdto.status
            )
            taskRepository.save(updatedTask)
        } else {
            null
        }
    }

    fun deleteTask(id: Long) {
        taskRepository.deleteById(id)
    }
}