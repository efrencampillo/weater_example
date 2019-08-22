package com.example.weater.viewmodel

import com.example.weater.domain.BaseResponse

open class EventClass

class SuccessEvent(val response: BaseResponse) : EventClass()

class ErrorEvent(val error: Throwable) : EventClass()
