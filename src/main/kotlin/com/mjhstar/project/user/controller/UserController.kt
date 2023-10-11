package com.mjhstar.project.user.controller

import com.mjhstar.project.common.support.extension.TimeUtils
import com.mjhstar.project.common.model.CommonResponse
import com.mjhstar.project.user.model.request.UserCreateRequest
import com.mjhstar.project.user.model.response.UserCreateResponse
import com.mjhstar.project.user.model.response.UserGetResponse
import com.mjhstar.project.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/{apiVersion}/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("")
    fun createUser(
        @PathVariable apiVersion: String,
        @RequestBody request: UserCreateRequest,
    ): CommonResponse<UserCreateResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = userService.createUser(request.toModel())
        return CommonResponse.success(
            data = UserCreateResponse.createBy(responseModel),
            requestTime = requestTime
        )
    }

    @GetMapping("")
    fun getUser(
        @PathVariable apiVersion: String,
        @RequestParam userId: String
    ): CommonResponse<UserGetResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = userService.getUser(userId)
        return CommonResponse.success(
            data = UserGetResponse.createBy(responseModel),
            requestTime = requestTime
        )
    }
}