package com.example

interface ConvertService {

    suspend fun getRate(from: String, to: String): Double

}
