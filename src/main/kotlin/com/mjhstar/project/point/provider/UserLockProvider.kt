package com.mjhstar.project.point.provider

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class UserLockProvider{
    private val userLocks = ConcurrentHashMap<Long, Any>()
    fun getUserLock(userIdx: Long): Any{
        return userLocks.computeIfAbsent(userIdx){Any()}
    }
}