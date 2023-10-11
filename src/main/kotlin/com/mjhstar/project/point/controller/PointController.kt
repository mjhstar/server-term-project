package com.mjhstar.project.point.controller

import com.mjhstar.project.common.model.CommonResponse
import com.mjhstar.project.common.support.extension.TimeUtils
import com.mjhstar.project.point.model.dto.PointTxResponseDto
import com.mjhstar.project.point.model.request.PointChargeRequest
import com.mjhstar.project.point.model.request.PointGetHistoriesRequest
import com.mjhstar.project.point.model.request.PointUseCancelRequest
import com.mjhstar.project.point.model.request.PointUseRequest
import com.mjhstar.project.point.model.response.PointChargeResponse
import com.mjhstar.project.point.model.response.PointGetResponse
import com.mjhstar.project.point.model.response.PointUseCancelResponse
import com.mjhstar.project.point.model.response.PointUseResponse
import com.mjhstar.project.point.service.PointService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/{apiVersion}/point")
class PointController(
    private val pointService: PointService
) {
    @GetMapping("")
    fun getPoint(
        @PathVariable apiVersion: String,
        @RequestParam userIdx: Long
    ): CommonResponse<PointGetResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = pointService.getPoint(userIdx)
        return CommonResponse.success(
            data = PointGetResponse.createBy(responseModel),
            requestTime = requestTime
        )
    }

    @PostMapping("/charge")
    fun savePoint(
        @PathVariable apiVersion: String,
        @RequestBody request: PointChargeRequest
    ): CommonResponse<PointChargeResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = pointService.chargePoint(request.toModel())
        return CommonResponse.success(
            data = PointChargeResponse.createBy(responseModel),
            requestTime = requestTime
        )
    }

    @PostMapping("/use")
    fun usePoint(
        @PathVariable apiVersion: String,
        @RequestBody request: PointUseRequest
    ): CommonResponse<PointUseResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = pointService.usePoint(request.toModel())
        return CommonResponse.success(
            data = PointUseResponse.createBy(responseModel),
            requestTime = requestTime
        )
    }

    @PutMapping("/cancel")
    fun cancelUsePoint(
        @PathVariable apiVersion: String,
        @RequestBody request: PointUseCancelRequest
    ): CommonResponse<PointUseCancelResponse> {
        val requestTime = TimeUtils.currentTimeMillis()
        val responseModel = pointService.cancelUsePoint(request.toModel())
        return CommonResponse.success(
            data = PointUseCancelResponse.createBy(responseModel),
            requestTime = requestTime
        )

    }

    @GetMapping("/history")
    fun getPointHistories(
        @PathVariable apiVersion: String,
        @ModelAttribute condition: PointGetHistoriesRequest
    ): CommonResponse<Page<PointTxResponseDto>> {
        val requestTime = TimeUtils.currentTimeMillis()
        val response = pointService.getPointHistories(condition.toModel())
        return CommonResponse.success(
            data = response,
            requestTime = requestTime
        )
    }
}