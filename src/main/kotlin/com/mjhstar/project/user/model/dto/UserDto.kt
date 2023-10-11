package com.mjhstar.project.user.model.dto

import com.mjhstar.project.user.entity.User

class UserDto(
    val userIdx: Long,
    val userName: String,
    val userId: String
) {
    companion object {
        fun createBy(user: User) = UserDto(
            userIdx = user.userIdx,
            userName = user.userName,
            userId = user.userId
        )
    }
}