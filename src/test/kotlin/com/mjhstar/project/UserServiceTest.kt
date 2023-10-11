package com.mjhstar.project

import com.mjhstar.project.user.entity.User
import com.mjhstar.project.user.model.dto.UserDto
import com.mjhstar.project.user.model.request.UserCreateRequestModel
import com.mjhstar.project.user.model.response.UserCreateResponseModel
import com.mjhstar.project.user.model.response.UserGetResponseModel
import com.mjhstar.project.user.provider.UserProvider
import com.mjhstar.project.user.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    private lateinit var userProvider: UserProvider

    @Test
    fun createUserTest() {
        val request = UserCreateRequestModel(userName = "홍길동", userId = "gildong")
        val user = User(userIdx = 1L, userName = "홍길동", userId = "gildong")
        val userDto = UserDto.createBy(user)

        whenever(userProvider.registerUser(request)).thenReturn(userDto)

        val response = userService.createUser(request)

        assertEquals(UserCreateResponseModel.createBy(userDto).userId, response.userId)
        assertEquals(UserCreateResponseModel.createBy(userDto).userName, response.userName)
        assertEquals(UserCreateResponseModel.createBy(userDto).userIdx, response.userIdx)
    }

    @Test
    fun getUserTest() {
        val userId = "gildong"
        val user = User(userIdx = 1L, userName = "홍길동", userId = userId)
        val userDto = UserDto.createBy(user)

        whenever(userProvider.getUserDto(userId)).thenReturn(userDto)

        val response = userService.getUser(userId)

        assertEquals(UserGetResponseModel.createBy(userDto).userId, response.userId)
        assertEquals(UserGetResponseModel.createBy(userDto).userIdx, response.userIdx)
        assertEquals(UserGetResponseModel.createBy(userDto).userName, response.userName)
    }
}