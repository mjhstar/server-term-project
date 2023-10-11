package com.mjhstar.project.user.repository

import com.mjhstar.project.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun existsByUserId(userId: String): Boolean
    fun existsByUserIdx(userIdx: Long): Boolean
    fun findByUserId(userId: String): User?
    fun findByUserIdx(userIdx: Long): User?
}
