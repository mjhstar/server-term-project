package com.mjhstar.project.point.repository

import com.mjhstar.project.point.model.condition.PointTxCondition
import com.mjhstar.project.point.model.dto.PointTxResponseDto
import org.springframework.data.domain.Page

interface PointTxCustomRepository {
    fun searchPointTxList(condition: PointTxCondition): Page<PointTxResponseDto>
}