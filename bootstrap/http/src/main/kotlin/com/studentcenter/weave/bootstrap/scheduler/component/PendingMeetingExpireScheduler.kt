package com.studentcenter.weave.bootstrap.scheduler.component

import com.studentcenter.weave.application.meeting.port.inbound.CancelEndedPendingMeeting
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PendingMeetingExpireScheduler(
    private val cancelEndedPendingMeeting: CancelEndedPendingMeeting,
) {

    // 1분마다 돌아감
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    fun expireMeeting() = cancelEndedPendingMeeting.invoke()
}
