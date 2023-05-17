package com.example

import com.example.plugins.logger
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.response.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.util.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.handleRoot() {
    call.respond(ThymeleafContent("conversion-form", mapOf()))
}

suspend fun PipelineContext<Unit, ApplicationCall>.handleConvert(convertService: ConvertService) {
    try {
        val formParams = parseFormParameters()

        val rate = convertService.getRate(formParams.from, formParams.to)
        val result = convertService.convert(formParams.amount, rate)
        call.respond(
            ThymeleafContent(
                "conversion-result",
                mapOf("result" to result)
            )
        )
    } catch (e: IllegalArgumentException) {
        call.respond(
            ThymeleafContent(
                "conversion-error",
                mapOf("error" to e.localizedMessage)
            )
        )
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.parseFormParameters(): ConvertFormParams {
    try {
        val formParams = call.receiveParameters()
        // check currency format 3 chars
        // check api supports from
        val from = formParams.getOrFail("from")
        // check currency format 3 chars
        // check api supports to
        val to = formParams.getOrFail("to")

        // check amount not negative
        // check amount is decimal
        val amountString = formParams.getOrFail("amount")
        val amount = amountString.toDouble()
        if (amount < 0.0) {
            throw IllegalArgumentException("Amount cannot be negative number")
        }

        return ConvertFormParams(
            from,
            to,
            amount,
        )
    } catch (e: ContentTransformationException) {
        logger.error("form parameters not provided", e)
        throw IllegalArgumentException("form parameters not provided")
    } catch (e: MissingRequestParameterException) {
        throw IllegalArgumentException("${e.parameterName} cannot be empty")
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException("Amount should be a decimal number")
    }
}
