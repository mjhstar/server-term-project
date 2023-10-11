package com.mjhstar.project.point.repository

import com.mjhstar.project.point.entity.PointTx
import com.mjhstar.project.point.enums.PointTxState
import org.springframework.data.jpa.repository.JpaRepository

interface PointTxRepository : JpaRepository<PointTx, Long> {
    fun findByTxIdxAndUserUserIdxAndState(txIdx: Long, userIdx: Long, state: PointTxState): PointTx?
}

fun PointTxRepository.findUseTx(txIdx: Long, userIdx: Long): PointTx? {
    return findByTxIdxAndUserUserIdxAndState(txIdx, userIdx, PointTxState.USED)
}
