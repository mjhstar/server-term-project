package com.mjhstar.project.user.model.response

class UserCreateResponse(
    val userIdx: Long,
    val userName: String,
    val userId: String,
) {
    companion object{
        fun createBy(model: UserCreateResponseModel): UserCreateResponse {
            return UserCreateResponse(
                userIdx = model.userIdx,
                userName = model.userName,
                userId = model.userId
            )
        }
    }
}