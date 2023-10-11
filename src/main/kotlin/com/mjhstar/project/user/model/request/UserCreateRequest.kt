package com.mjhstar.project.user.model.request

import com.mjhstar.project.common.support.extension.isNullOrEmptyOrBlank
import com.mjhstar.project.common.web.BusinessException
import com.mjhstar.project.common.web.ErrorCode

class UserCreateRequest(
    private val userName: String,
    private val userId: String,
) {
    fun toModel(): UserCreateRequestModel {
        if(userName.isNullOrEmptyOrBlank()){
            throw BusinessException(ErrorCode.INVALID_USER_NAME)
        }
        if(userId.isNullOrEmptyOrBlank()){
            throw BusinessException(ErrorCode.INVALID_USER_ID)
        }
        return UserCreateRequestModel(
            userName = this.userName,
            userId = this.userId
        )
    }
}