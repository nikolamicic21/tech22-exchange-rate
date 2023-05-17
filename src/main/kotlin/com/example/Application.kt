package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureTemplating
import com.example.plugins.getClient
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    val client = getClient()
    val convertService = ConvertServiceImpl(client)

    configureTemplating()
    configureRouting(convertService)
}
