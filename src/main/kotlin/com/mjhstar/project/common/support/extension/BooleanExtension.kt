package com.mjhstar.project.common.support.extension

import com.mjhstar.project.common.web.BusinessException
import com.mjhstar.project.common.web.ErrorCode

fun Boolean.isTrueThenThrow(errorCode: ErrorCode) {
    if (this) throw BusinessException(errorCode)
}

fun Boolean.isFalseThenThrow(errorCode: ErrorCode) {
    if (!this) throw BusinessException(errorCode)
}
