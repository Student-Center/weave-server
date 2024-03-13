package com.studentcenter.weave.bootstrap.scheduler.component

import com.studentcenter.weave.infrastructure.persistence.meeting.repository.MeetingJpaRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PendingMeetingExpireScheduler(
    private val meetingJpaRepository: MeetingJpaRepository
) {

    // 매일 1시
    @Scheduled(cron = "0 1 * * *", zone = "Asia/Seoul")
    fun expireMeeting() = meetingJpaRepository.expiredMeeting()
}
