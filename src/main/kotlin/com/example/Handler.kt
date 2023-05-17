package com.example

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.thymeleaf.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.handleRoot() {
    call.respond(ThymeleafContent("conversion-form", mapOf()))
}

suspend fun PipelineContext<Unit, ApplicationCall>.handleConvert(convertService: ConvertService) {
    val formParams = parseFormParameters()

    val rate = convertService.getRate(formParams.from, formParams.to)
    val result = convert(formParams.amount, rate)
    call.respond(
        ThymeleafContent(
            "conversion-result",
            mapOf("result" to result)
        )
    )
}

suspend fun PipelineContext<Unit, ApplicationCall>.parseFormParameters(): ConvertFormParams {
    val formParams = call.receiveParameters()
    return ConvertFormParams(
        formParams["from"]!!.toUpperCasePreservingASCIIRules(),
        formParams["to"]!!.toUpperCasePreservingASCIIRules(),
        formParams["amount"]!!.toUpperCasePreservingASCIIRules().toDouble(),
    )
}
