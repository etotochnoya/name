package ru.ac.uniyar.domain

import java.lang.IllegalArgumentException

class Projects(myProjects: List<Project>) {
    private val projects = myProjects.toMutableList()

    fun add(project: Project) {
        projects.add(project)
    }

    fun size() = projects.size

    fun getList(): List<Project> = projects

    fun takeToId(id: Int): Project {
        val tmpProjects = projects
        val project = tmpProjects.find { project -> project.id == id } ?: throw IllegalArgumentException()
        return project
    }
}
