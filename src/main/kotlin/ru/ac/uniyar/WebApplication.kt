package ru.ac.uniyar

// import org.http4k.core.*
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.ac.uniyar.domain.Project
import ru.ac.uniyar.domain.Projects
import ru.ac.uniyar.models.MainPageVM
import ru.ac.uniyar.models.ProjectVM
import ru.ac.uniyar.models.ProjectsVM
import java.time.LocalDateTime

fun showMainPage(renderer: TemplateRenderer): HttpHandler = {
    Response(Status.OK).body(renderer(MainPageVM()))
}
fun showProjectsPage(renderer: TemplateRenderer, projects: Projects): HttpHandler = {
    Response(Status.OK).body(renderer(ProjectsVM(projects.getList())))
}

fun showProject(renderer: TemplateRenderer, projectsList: List<Project>): HttpHandler = { request ->
    val id = request.path("id").toString()
    val projects = Projects(projectsList)
    val project = projects.takeToId(id.toInt())
    Response(OK).body(
        renderer(
            ProjectVM(
                project.id,
                project.addTime,
                project.projectName,
                project.entrepreneur,
                project.description,
                project.targetFundSize,
                project.startDate,
                project.endDate,
            ),
        ),
    )
}

fun router(renderer: TemplateRenderer, projects: Projects): HttpHandler = routes(
    "/" bind GET to showMainPage(renderer),
    "/project" bind GET to showProjectsPage(renderer, projects),
    "/project/{id}" bind GET to showProject(renderer, projects.getList()),
    static(ResourceLoader.Classpath("/ru/ac/uniyar/public/")),
)

fun fillData(): Projects {
    val projects = Projects(listOf())
    projects.add(
        Project(
            projects.size(),
            "Онлайн сервис такси",
            "Райан Гослинг",
            "Закажи такси онлайн или по телефону и по низкой цене по всей стране",
            1000000,
            LocalDateTime.of(2011, 9, 16, 0, 0, 0),
            LocalDateTime.of(2011, 11, 3, 0, 0, 0),
        ),
    )

    projects.add(
        Project(
            projects.size(),
            "Печать визиток",
            "Патрик Бэйтмен",
            "Быстрая и качественная печать визиток по вашему макету",
            300000,
            LocalDateTime.of(2000, 1, 21, 0, 0, 0),
            LocalDateTime.of(2000, 4, 14, 0, 0, 0),
        ),
    )

    return projects
}

fun main() {
    val projects = fillData()

    val renderer = PebbleTemplates().HotReload("src/main/resources")
    val app = router(renderer, projects)

    val printingApp: HttpHandler = PrintRequest().then(app)
    val server = printingApp.asServer(Jetty(9000)).start() // ?))
    println("Server started on " + server.port())
}