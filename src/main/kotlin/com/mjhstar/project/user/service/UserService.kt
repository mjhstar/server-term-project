package com.mjhstar.project.user.service

import com.mjhstar.project.user.model.request.UserCreateRequestModel
import com.mjhstar.project.user.model.response.UserCreateResponseModel
import com.mjhstar.project.user.model.response.UserGetResponseModel
import com.mjhstar.project.user.provider.UserProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userProvider: UserProvider
) {
    @Transactional
    fun createUser(request: UserCreateRequestModel): UserCreateResponseModel {
        val user = userProvider.registerUser(request)
        return UserCreateResponseModel.createBy(user)
    }

    @Transactional(readOnly = true)
    fun getUser(userId: String): UserGetResponseModel {
        val user = userProvider.getUserDto(userId)
        return UserGetResponseModel.createBy(user)
    }
}