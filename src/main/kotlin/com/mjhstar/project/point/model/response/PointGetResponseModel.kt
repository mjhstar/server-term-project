package com.mjhstar.project.point.model.response

import com.mjhstar.project.point.model.dto.PointDto

class PointGetResponseModel(
    val userIdx: Long,
    val remainPoints: Long
) {
    companion object {
        fun createBy(point: PointDto) = PointGetResponseModel(
            userIdx = point.userIdx,
            remainPoints = point.remainPoints
        )
    }
}