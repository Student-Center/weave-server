package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.support.common.vo.Url

interface MeetingTeamGetByInvitationLinkUseCase {

    fun invoke(invitationLink: Url): MeetingTeam

}
