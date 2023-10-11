package com.mjhstar.project

import com.mjhstar.project.point.model.request.PointUseRequestModel
import com.mjhstar.project.point.provider.PointChargeProvider
import com.mjhstar.project.point.provider.PointProvider
import com.mjhstar.project.point.provider.PointTxProvider
import com.mjhstar.project.point.provider.PointUseHistoryProvider
import com.mjhstar.project.point.service.PointService
import com.mjhstar.project.user.provider.UserProvider
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringJUnitConfig
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("PointService Concurrent Test")
class PointServiceConcurrentTest {

    @Autowired
    private lateinit var pointService: PointService

    @MockBean
    private lateinit var pointProvider: PointProvider

    @MockBean
    private lateinit var pointChargeProvider: PointChargeProvider

    @MockBean
    private lateinit var pointUseHistoryProvider: PointUseHistoryProvider

    @MockBean
    private lateinit var pointTxProvider: PointTxProvider

    @MockBean
    private lateinit var userProvider: UserProvider

    @Test
    @RepeatedTest(10)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun usePointTest() {
        val userIdx = 1L
        val usePoint = 100L

        val latch = CountDownLatch(2)
        val executor: ExecutorService = Executors.newFixedThreadPool(2)

        repeat(2) {
            executor.submit {
                try {
                    pointService.usePoint(PointUseRequestModel(userIdx, usePoint))
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
    }
}