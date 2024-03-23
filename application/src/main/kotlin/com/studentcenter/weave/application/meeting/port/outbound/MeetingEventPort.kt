package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.user.vo.Mbti

fun interface MeetingEventPort {

    fun sendMeetingIsMatchedMessage(
        meeting: Meeting,
        memberCount: Int,
        matchedMeetingCount: Int,
        requestingMeetingTeamMbti: Mbti,
        receivingMeetingTeamMbti: Mbti,
    )

}
