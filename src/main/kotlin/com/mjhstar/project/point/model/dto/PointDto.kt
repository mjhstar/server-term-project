package com.mjhstar.project.point.model.dto

import com.mjhstar.project.point.entity.Point

class PointDto(
    val userIdx: Long,
    val remainPoints: Long
) {
    companion object{
        fun createBy(point: Point) = PointDto(
            userIdx = point.user.userIdx,
            remainPoints = point.remainPoints
        )
    }
}