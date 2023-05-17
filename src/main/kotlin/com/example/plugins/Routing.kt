package com.example.plugins

import com.example.ConvertService
import com.example.handleConvert
import com.example.handleRoot
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger(Routing::class.java)

fun Application.configureRouting(convertService: ConvertService) {
    routing {
        get("/") {
            handleRoot()
        }
        post("/convert") {
            handleConvert(convertService)
        }
    }
}
