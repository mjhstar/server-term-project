package com.mjhstar.project.point.repository

import com.mjhstar.project.point.entity.PointUseHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PointUseHistoryRepository : JpaRepository<PointUseHistory, Long> {
    fun findByIdxIn(ids: List<Long>): List<PointUseHistory>
}