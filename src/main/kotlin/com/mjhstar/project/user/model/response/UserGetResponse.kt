package com.mjhstar.project.user.model.response

class UserGetResponse(
    val userIdx: Long,
    val userName: String,
    val userId: String,
) {
    companion object{
        fun createBy(model: UserGetResponseModel): UserGetResponse {
            return UserGetResponse(
                userIdx = model.userIdx,
                userName = model.userName,
                userId = model.userId
            )
        }
    }
}