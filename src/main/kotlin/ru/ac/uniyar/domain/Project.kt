package ru.ac.uniyar.domain

import java.time.LocalDateTime

class Project(
    val id: Int,
    val projectName: String,
    val entrepreneur: String,
    val description: String,
    val targetFundSize: Long,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val addTime: LocalDateTime = LocalDateTime.now(),
)
