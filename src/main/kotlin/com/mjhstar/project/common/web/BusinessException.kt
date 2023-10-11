package com.mjhstar.project.common.web

import org.springframework.http.HttpStatus

open class BusinessException(
    val code: String,
    val httpStatus: HttpStatus,
    override val message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause) {
    constructor(errorCode: ErrorCode, messageMapper: ((String) -> String)? = null, cause: Throwable? = null) : this(
        errorCode.errorCode,
        errorCode.httpStatus,
        messageMapper?.let { messageMapper(errorCode.message) } ?: errorCode.message,
        cause
    )
}
