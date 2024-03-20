package com.studentcenter.weave.bootstrap.scheduler.component

import com.studentcenter.weave.application.meeting.port.inbound.CancelEndedPendingMeetingUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PendingMeetingExpireScheduler(
    private val useCase: CancelEndedPendingMeetingUseCase
) {

    // 1분마다 돌아감
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    fun expireMeeting() = useCase.invoke()
}
