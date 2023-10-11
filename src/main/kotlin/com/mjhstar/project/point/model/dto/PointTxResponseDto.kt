package com.mjhstar.project.point.model.dto

import com.mjhstar.project.common.support.extension.toOffsetDateTime
import com.mjhstar.project.point.entity.PointTx
import com.mjhstar.project.point.enums.PointTxState
import java.time.OffsetDateTime

class PointTxResponseDto(
    val txIdx: Long,
    val userIdx: Long,
    val state: PointTxState,
    val points: Long,
    val createdAt: OffsetDateTime,
    val usedAt: OffsetDateTime?,
    val canceledAt: OffsetDateTime?
) {
    companion object {
        fun createBy(pointTx: PointTx): PointTxResponseDto {
            return PointTxResponseDto(
                txIdx = pointTx.txIdx,
                userIdx = pointTx.user.userIdx,
                state = pointTx.state,
                points = pointTx.points,
                createdAt = pointTx.createdAt.toOffsetDateTime(),
                usedAt = pointTx.usedAt?.toOffsetDateTime(),
                canceledAt = pointTx.canceledAt?.toOffsetDateTime()
            )
        }
    }
}