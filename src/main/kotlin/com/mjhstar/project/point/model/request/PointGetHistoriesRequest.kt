package com.mjhstar.project.point.model.request

import com.mjhstar.project.common.support.extension.TimeUtils
import com.mjhstar.project.common.support.extension.toLocalDate
import com.mjhstar.project.common.web.BusinessException
import com.mjhstar.project.common.web.ErrorCode
import com.mjhstar.project.point.enums.PointTxState
import java.time.LocalDate

class PointGetHistoriesRequest(
    val userIdx: Long,
    val type: PointTxState?,
    val from: String?,
    val to: String?,
    val size: Int?,
    val page: Int?,
) {
    fun toModel(): PointGetHistoriesRequestModel {
        val convertFrom: LocalDate?
        val convertTo: LocalDate?

        try {
            convertFrom = from?.toLocalDate()
            convertTo = to?.toLocalDate()
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.FAIL_CONVERT_DATE)
        }

        if (convertFrom != null && convertFrom.plusMonths(6) < convertTo) {
            throw BusinessException(ErrorCode.FAIL_INQUIRY_DATE)
        }

        if (size != null && size > 100) {
            throw BusinessException(ErrorCode.TOO_LARGE_PAGE_SIZE)
        }

        return PointGetHistoriesRequestModel(
            userIdx = this.userIdx,
            type = this.type,
            from = convertFrom ?: TimeUtils.currentLocalDate().minusMonths(3),
            to = convertTo ?: TimeUtils.currentLocalDate(),
            size = this.size,
            page = this.page

        )
    }
}