package com.example

import java.math.BigDecimal
import java.math.RoundingMode

fun convert(amount: Double, rate: Double): Double {
    val amountDecimal = BigDecimal.valueOf(amount)
    val rateDecimal = BigDecimal.valueOf(rate)

    return amountDecimal
        .multiply(rateDecimal)
        .setScale(6, RoundingMode.HALF_EVEN)
        .toDouble()
}
