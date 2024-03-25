package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.application.meeting.vo.MeetingMatchingInfo

fun interface MeetingEventPort {

    fun sendMeetingIsMatchedMessage(
        meetingMatchingInfo: MeetingMatchingInfo,
    )

}
