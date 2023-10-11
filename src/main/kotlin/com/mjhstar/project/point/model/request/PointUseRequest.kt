package com.mjhstar.project.point.model.request

class PointUseRequest(
    private val userIdx: Long,
    private val usePoints: Long
) {
    fun toModel() = PointUseRequestModel(
        userIdx = this.userIdx,
        usePoints = this.usePoints
    )
}