package com.mjhstar.project.user.entity

import com.mjhstar.project.common.support.extension.TimeUtils
import com.mjhstar.project.point.entity.Point
import javax.persistence.*

@Entity(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userIdx: Long = 0L,
    val userName: String,
    @Column(unique = true)
    val userId: String,
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val point: Point? = null,
    val createdAt: Long = TimeUtils.currentTimeMillis(),
) {

}