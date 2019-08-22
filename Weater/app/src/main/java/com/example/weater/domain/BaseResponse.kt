package com.example.weater.domain

data class BaseResponse(
    val latitude: Double?,
    val longitude: Double?,
    val timezone: String?,
    val hourly: EnlapsedInfo,
    val daily: EnlapsedInfo)