package com.mjhstar.project.user.model.response

import com.mjhstar.project.user.model.dto.UserDto

class UserCreateResponseModel(
    val userIdx: Long,
    val userName: String,
    val userId: String,
) {
    companion object {
        fun createBy(user: UserDto) = UserCreateResponseModel(
            userIdx = user.userIdx,
            userName = user.userName,
            userId = user.userId
        )
    }
}