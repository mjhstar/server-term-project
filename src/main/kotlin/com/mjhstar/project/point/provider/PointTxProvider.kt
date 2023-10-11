package com.mjhstar.project.point.provider

import com.mjhstar.project.common.support.extension.TimeUtils
import com.mjhstar.project.common.web.BusinessException
import com.mjhstar.project.common.web.ErrorCode
import com.mjhstar.project.point.entity.PointTx
import com.mjhstar.project.point.enums.PointTxState
import com.mjhstar.project.point.model.dto.PointTxDto
import com.mjhstar.project.point.model.dto.PointTxResponseDto
import com.mjhstar.project.point.model.request.PointGetHistoriesRequestModel
import com.mjhstar.project.point.repository.PointTxCustomRepository
import com.mjhstar.project.point.repository.PointTxRepository
import com.mjhstar.project.point.repository.findUseTx
import com.mjhstar.project.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class PointTxProvider(
    private val pointTxRepository: PointTxRepository,
    private val pointTxCustomRepository: PointTxCustomRepository
) {
    fun createPointTxAndGetEntity(user: User, points: Long, state: PointTxState): PointTx {
        val pointTx = PointTx(
            user = user,
            points = points,
            state = state,
            usedAt = if (state == PointTxState.USED) TimeUtils.currentTimeMillis() else null
        )
        return pointTxRepository.save(pointTx)
    }

    fun cancelTxAndGetDto(userIdx: Long, txIdx: Long): PointTxDto {
        val pointTx = pointTxRepository.findUseTx(txIdx, userIdx)
            ?: throw BusinessException(ErrorCode.NOT_EXIST_TX)
        return PointTxDto.createBy(pointTxRepository.save(pointTx.cancel()))
    }

    fun getHistories(request: PointGetHistoriesRequestModel): Page<PointTxResponseDto> {
        return pointTxCustomRepository.searchPointTxList(request.toCondition())
    }
}