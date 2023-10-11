package com.mjhstar.project

import com.mjhstar.project.common.support.extension.TimeUtils
import com.mjhstar.project.common.support.extension.plusYear
import com.mjhstar.project.common.support.extension.toLocalDate
import com.mjhstar.project.common.support.extension.toOffsetDateTime
import com.mjhstar.project.point.entity.Point
import com.mjhstar.project.point.entity.PointCharge
import com.mjhstar.project.point.entity.PointTx
import com.mjhstar.project.point.enums.PointState
import com.mjhstar.project.point.enums.PointTxState
import com.mjhstar.project.point.model.dto.PointCancelDto
import com.mjhstar.project.point.model.dto.PointDto
import com.mjhstar.project.point.model.dto.PointTxDto
import com.mjhstar.project.point.model.dto.PointTxResponseDto
import com.mjhstar.project.point.model.request.PointChargeRequestModel
import com.mjhstar.project.point.model.request.PointGetHistoriesRequestModel
import com.mjhstar.project.point.model.request.PointUseCancelRequestModel
import com.mjhstar.project.point.model.request.PointUseRequestModel
import com.mjhstar.project.point.model.response.PointGetResponseModel
import com.mjhstar.project.point.service.PointService
import com.mjhstar.project.user.entity.User
import com.mjhstar.project.user.provider.UserProvider
import com.mjhstar.project.point.provider.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl

@SpringBootTest
class PointServiceTest {

    @InjectMocks
    private lateinit var pointService: PointService

    @Mock
    private lateinit var pointService2: PointService

    @Mock
    private lateinit var pointProvider: PointProvider

    @Mock
    private lateinit var pointChargeProvider: PointChargeProvider

    @Mock
    private lateinit var pointUseHistoryProvider: PointUseHistoryProvider

    @Mock
    private lateinit var pointTxProvider: PointTxProvider

    @Mock
    private lateinit var userProvider: UserProvider

    @Mock
    private lateinit var userLockProvider: UserLockProvider

    @Test
    fun getPointTest() {
        val userIdx = 1L
        val expectedPointDto = PointDto(userIdx = userIdx, remainPoints = 0)
        `when`(userProvider.isExistUser(userIdx)).thenReturn(true)
        `when`(pointProvider.getPoint(userIdx)).thenReturn(expectedPointDto)

        val result = pointService.getPoint(userIdx)

        assertEquals(PointGetResponseModel.createBy(expectedPointDto).userIdx, result.userIdx)
        assertEquals(PointGetResponseModel.createBy(expectedPointDto).remainPoints, result.remainPoints)
    }

    @Test
    fun getPointHistoriesTest() {
        val request = PointGetHistoriesRequestModel(
            userIdx = 1L,
            from = "20230801".toLocalDate(),
            to = "2023-08-16".toLocalDate()
        )
        val pointTxList = emptyList<PointTxResponseDto>()
        val expectedPage = PageImpl(pointTxList)

        `when`(pointTxProvider.getHistories(request)).thenReturn(expectedPage)

        val result = pointService.getPointHistories(request)

        assertEquals(expectedPage, result)
    }

    @Test
    fun chargePointTest() {
        val request = PointChargeRequestModel(userIdx = 1L, chargePoints = 100)
        val user = User(
            userIdx = request.userIdx,
            userName = "",
            userId = ""
        )
        val userLock = Any()
        `when`(userLockProvider.getUserLock(request.userIdx)).thenReturn(userLock)
        `when`(userProvider.getUserEntity(request.userIdx)).thenReturn(user)
        `when`(pointProvider.chargePointAndGetDto(user, request.chargePoints)).thenReturn(
            PointDto(
                userIdx = request.userIdx,
                remainPoints = 100
            )
        )
        `when`(
            pointTxProvider.createPointTxAndGetEntity(
                user = user,
                points = request.chargePoints,
                state = PointTxState.CHARGE
            )
        ).thenReturn(
            PointTx(
                user = user,
                state = PointTxState.CHARGE,
                points = 100L,
                pointCharge = null,
                usedAt = null,
                canceledAt = null,
                createdAt = 0,
                txIdx = 1L,
                pointUesHistories = emptyList()
            )
        )

        val result = pointService.chargePoint(request)

        assertNotNull(result)
    }

    @Test
    fun usePointTest() {
        val currentTime = TimeUtils.currentTimeMillis()
        val request = PointUseRequestModel(userIdx = 1L, usePoints = 100L)
        val user = User(
            userIdx = request.userIdx,
            userId = "",
            userName = ""
        )
        val chargePointTx = PointTx(
            txIdx = 1L,
            user = user,
            usedAt = currentTime,
            state = PointTxState.CHARGE,
            points = 200L,
            pointUesHistories = emptyList(),
            createdAt = currentTime
        )
        val pointCharge = PointCharge(
            idx = 1L,
            chargePoints = 200L,
            remainPoints = 200L,
            pointTx = chargePointTx,
            createdAt = currentTime,
            pointUesHistories = emptyList(),
            state = PointState.ACTIVE,
            user = user,
            expireAt = currentTime.plusYear(1)
        )
        val pointTx = PointTx(
            txIdx = 2L,
            user = user,
            usedAt = currentTime,
            state = PointTxState.USED,
            points = request.usePoints,
            pointUesHistories = emptyList(),
            createdAt = currentTime,
            pointCharge = pointCharge,
            canceledAt = null
        )
        val pointChargeList = listOf(pointCharge)
        val userLock = Any()

        `when`(userLockProvider.getUserLock(request.userIdx)).thenReturn(userLock)
        `when`(pointProvider.isAvailableUsePoint(request.userIdx, request.usePoints)).thenReturn(true)
        `when`(userProvider.getUserEntity(request.userIdx)).thenReturn(user)
        `when`(
            pointTxProvider.createPointTxAndGetEntity(
                user = user,
                points = request.usePoints,
                state = PointTxState.USED
            )
        ).thenReturn(pointTx)
        `when`(pointChargeProvider.getPointChargeEntityList(request.userIdx, request.usePoints)).thenReturn(
            pointChargeList
        )
        `when`(pointService2.pointValidCheck(request.usePoints, pointChargeList)).thenReturn(true)
        `when`(pointProvider.usePointAndGetEntity(request.userIdx, request.usePoints)).thenReturn(
            Point(
                user = user,
                remainPoints = 100L,
                idx = 1L,
                updatedAt = currentTime
            )
        )
        val result = pointService.usePoint(request)
        assertNotNull(result)
    }

    @Test
    fun cancelUsePointTest() {
        val request = PointUseCancelRequestModel(userIdx = 1L, txIdx = 1L)
        val userLock = Any()

        `when`(userLockProvider.getUserLock(request.userIdx)).thenReturn(userLock)
        `when`(
            pointTxProvider.cancelTxAndGetDto(
                request.userIdx,
                request.txIdx
            )
        ).thenReturn(
            PointTxDto(
                txIdx = 1L,
                points = 100L,
                userIdx = request.userIdx,
                state = PointTxState.CHARGE,
                pointHistoryIds = emptyList(),
                pointChargeIdx = 0L,
                usedAt = null,
                createdAt = TimeUtils.currentTimeMillis().toOffsetDateTime(),
                canceledAt = null
            )
        )
        `when`(pointUseHistoryProvider.cancelAndGetCancelDto(any())).thenReturn(
            listOf(
                PointCancelDto(
                    chargeIdx = 1L,
                    usedPoints = 100L
                )
            )
        )
        `when`(pointProvider.useCancelAndGetDto(request.userIdx, 100L)).thenReturn(
            PointDto(
                userIdx = request.userIdx,
                remainPoints = 100L
            )
        )

        val result = pointService.cancelUsePoint(request)

        assertNotNull(result)
    }
}