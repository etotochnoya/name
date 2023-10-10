package ru.ac.uniyar.models
import org.http4k.template.ViewModel
import java.time.LocalDateTime

class ProjectVM(
    val id: Int,
    val addTime: LocalDateTime,
    val projectName: String,
    val entrepreneur: String,
    val description: String,
    val targetFundSize: Long,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
) : ViewModel
