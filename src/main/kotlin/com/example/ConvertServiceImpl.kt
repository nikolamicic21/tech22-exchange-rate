package com.example

import com.example.plugins.logger
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.math.BigDecimal
import java.math.RoundingMode

class ConvertServiceImpl(private val client: HttpClient) : ConvertService {
    override suspend fun getRate(from: String, to: String): Double {
        val response = client.get {
            url("https://api.exchangerate.host/convert")
            parameter("from", from)
            parameter("to", to)
        }
        val convertResponse: ConvertResponseDto = response.body<ConvertResponseDto>()
        logger.info("Rate {}", convertResponse.info.rate)

        return convertResponse.info.rate
    }

    override suspend fun convert(amount: Double, rate: Double): Double {
        val amountDecimal = BigDecimal.valueOf(amount)
        val rateDecimal = BigDecimal.valueOf(rate)

        return amountDecimal
            .multiply(rateDecimal)
            .setScale(6, RoundingMode.HALF_EVEN)
            .toDouble()
    }
}
