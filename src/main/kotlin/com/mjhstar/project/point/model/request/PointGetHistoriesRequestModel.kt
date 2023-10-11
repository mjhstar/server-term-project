package com.mjhstar.project.point.model.request

import com.mjhstar.project.point.enums.PointTxState
import com.mjhstar.project.point.model.condition.PointTxCondition
import java.time.LocalDate

class PointGetHistoriesRequestModel(
    val userIdx: Long,
    val type: PointTxState? = null,
    val from: LocalDate,
    val to: LocalDate,
    val size: Int? = null,
    val page: Int? = null,
) {
    fun toCondition() = PointTxCondition(
        userIdx = this.userIdx,
        type = this.type,
        from = this.from,
        to = this.to,
        size = this.size,
        page = this.page
    )
}