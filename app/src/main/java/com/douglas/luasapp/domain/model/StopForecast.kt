package com.douglas.luasapp.domain.model

data class StopForecast (
    val stop: Stop,
    val direction: String?,
    val due: String?,
    val destination: String?
)