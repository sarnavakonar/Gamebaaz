package main

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import main.model.request.CreateUserRequest
import main.model.request.FavouriteRequestBody
import main.model.response.GenericResponse
import main.util.Constants.USERNAME
import repository.Repository

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/' path"
            validate { credentials ->
                if(Repository.checkIfUserExists(credentials.name, credentials.password)){
                    USERNAME = credentials.name
                    UserIdPrincipal(credentials.name)
                }
                else {
                    null
                }
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {

        post("/createUser") {
            val requestBody = call.receive<CreateUserRequest>()
            val username = requestBody.username
            val password = requestBody.password
            if(username.isNullOrEmpty()){
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(status = "FAILURE", message = "Username should not be empty")
                )
                return@post
            }
            if(password.isNullOrEmpty()){
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(status = "FAILURE", message = "Password should not be empty")
                )
                return@post
            }
            call.respond(Repository.createOrLoginUser(username, password))
        }

        authenticate("auth-basic") {

            get("/getAllGames") {
                call.respond(Repository.getAllGames())
            }

            post("/alterFav") {
                val requestBody = call.receive<FavouriteRequestBody>()
                val gameId = requestBody.gameId
                val addToFav = requestBody.addToFav
                if(gameId == -1){
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(status = "FAILURE", message = "Invalid game Id")
                    )
                    return@post
                }
                if (addToFav){
                    call.respond(Repository.addGameAsFavourite(gameId))
                }
                else{
                    call.respond(Repository.deleteGameAsFavourite(gameId))
                }
            }

            get("/getAllFavourites") {
                call.respond(Repository.getAllFavouritesForUser())
            }

            get("/searchGame") {
                val param = call.request.queryParameters["param"]
                call.respond(Repository.searchGame(param))
            }

            get("/getGamesBy") {
                val devId = call.request.queryParameters["devId"]
                if (devId.isNullOrBlank()){
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(status = "FAILURE", message = "Invalid developer Id")
                    )
                    return@get
                }
                call.respond(Repository.getGameByDev(devId.toInt()))
            }

            get("/getGameDetail") {
                val gameId = call.request.queryParameters["gameId"]
                if (gameId.isNullOrBlank()){
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(status = "FAILURE", message = "Invalid game Id")
                    )
                    return@get
                }
                call.respond(Repository.getGameDetail(gameId.toInt()))
            }

//            get("/session/increment") {
//                val session = call.sessions.get<MySession>() ?: MySession()
//                call.sessions.set(session.copy(count = session.count + 1))
//                call.respondText("Counter is ${session.count}. Refresh to increment.")
//            }
//
//            get("/json/gson") {
//                call.respond(mapOf("hello" to "world-hi"))
//            }
//
//            get("/download") {
//                val file = File("resources/files/img.jpg")
//                call.response.header(
//                    HttpHeaders.ContentDisposition,
//                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "img.jpg")
//                        .toString()
//                )
//                call.respondFile(file)
//            }
        }
    }
}

data class MySession(val count: Int = 0)

