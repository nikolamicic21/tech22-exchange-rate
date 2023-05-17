package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureTemplating
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting(object : ConvertService {
                override suspend fun getRate(from: String, to: String): Double {
                    TODO("Not yet implemented")
                }

                override suspend fun convert(amount: Double, rate: Double): Double {
                    TODO("Not yet implemented")
                }
            })
            configureTemplating()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
