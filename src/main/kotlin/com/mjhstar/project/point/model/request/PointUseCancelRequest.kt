package com.mjhstar.project.point.model.request

class PointUseCancelRequest(
    val userIdx: Long,
    val txIdx: Long
) {
    fun toModel() = PointUseCancelRequestModel(
        userIdx = this.userIdx,
        txIdx = this.txIdx
    )
}