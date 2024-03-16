package com.studentcenter.weave.bootstrap.scheduler.component

import com.studentcenter.weave.application.meeting.port.inbound.CancelEndedPendingMeetingUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PendingMeetingExpireScheduler(
    private val useCase: CancelEndedPendingMeetingUseCase
) {

    // 매일 1시
    @Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
    fun expireMeeting() = useCase.invoke()
}
