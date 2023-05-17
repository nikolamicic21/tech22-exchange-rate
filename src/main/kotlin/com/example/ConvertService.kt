package com.example

interface ConvertService {

    suspend fun getRate(from: String, to: String): Double
    suspend fun convert(amount: Double, rate: Double): Double

}
