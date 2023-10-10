package ru.ac.uniyar.models

import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.Project

class ProjectsVM(val projects: List<Project>) : ViewModel
