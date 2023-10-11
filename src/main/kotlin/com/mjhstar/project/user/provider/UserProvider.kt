package com.mjhstar.project.user.provider

import com.mjhstar.project.common.support.extension.isTrueThenThrow
import com.mjhstar.project.common.web.BusinessException
import com.mjhstar.project.common.web.ErrorCode
import com.mjhstar.project.user.entity.User
import com.mjhstar.project.user.model.dto.UserDto
import com.mjhstar.project.user.model.request.UserCreateRequestModel
import com.mjhstar.project.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserProvider(
    private val userRepository: UserRepository,
) {
    fun isExistUser(userIdx: Long): Boolean {
        return userRepository.existsByUserIdx(userIdx)
    }

    fun registerUser(request: UserCreateRequestModel): UserDto {
        userRepository.existsByUserId(request.userId).isTrueThenThrow(ErrorCode.EXIST_USER_ID)
        val user = userRepository.save(
            User(
                userName = request.userName,
                userId = request.userId
            )
        )
        return UserDto.createBy(user)
    }

    fun getUserDto(userId: String): UserDto {
        val user = userRepository.findByUserId(userId)
        requireNotNull(user) { throw BusinessException(ErrorCode.NOT_EXIST_USER) }
        return UserDto.createBy(user)
    }

    fun getUserEntity(userIdx: Long): User {
        val user = userRepository.findByUserIdx(userIdx)
        requireNotNull(user) { throw BusinessException(ErrorCode.NOT_EXIST_USER) }
        return user
    }

}