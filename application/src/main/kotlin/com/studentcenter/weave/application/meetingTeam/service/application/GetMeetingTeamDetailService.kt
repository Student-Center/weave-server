package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeamDetail
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetail
import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMeetingTeamDetailService(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val getUser: GetUser,
    private val getMajor: GetMajor,
    private val getUniversity: GetUniversity,
) : GetMeetingTeamDetail {

    @Transactional(readOnly = true)
    override fun invoke(command: GetMeetingTeamDetail.Command): GetMeetingTeamDetail.Result {
        val targetMeetingTeam = meetingTeamRepository.getById(command.meetingId)

        val affinityScore = findMyMeetingTeam()?.let {
            meetingTeamDomainService.calculateTeamMbtiAffinityScore(it, targetMeetingTeam)
        }

        return GetMeetingTeamDetail.Result(
            meetingTeam = targetMeetingTeam,
            members = getMeetingMemberDetailInfos(targetMeetingTeam),
            affinityScore = affinityScore,
        )
    }

    private fun getMeetingMemberDetailInfos(meetingTeam: MeetingTeam): List<MeetingMemberDetail> {
        return meetingTeam
            .members
            .map { member ->
                val user = getUser.getById(member.userId)
                MeetingMemberDetail.of(
                    user = user,
                    major = getMajor.getById(user.majorId),
                    university = getUniversity.getById(user.universityId),
                    role = member.role
                )
            }
    }

    private fun findMyMeetingTeam(): MeetingTeam? {
        return getCurrentUserAuthentication()
            .let { meetingTeamRepository.findByMemberUserId(it.userId) }
    }
}
