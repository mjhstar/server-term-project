package com.mjhstar.project.point.model.request

class PointChargeRequest(
    val userIdx: Long,
    val chargePoints: Long,
) {
    fun toModel() = PointChargeRequestModel(
        userIdx = this.userIdx,
        chargePoints = this.chargePoints
    )
}