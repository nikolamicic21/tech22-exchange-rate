package com.example

import kotlinx.serialization.Serializable

@Serializable
data class ConvertResponseDto(val info: InfoDto)

@Serializable
data class InfoDto(val rate: Double)

data class ConvertFormParams(val from: String, val to: String, val amount: Double)
