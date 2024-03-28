package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetDetailUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetailInfo
import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingTeamGetDetailApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val getUser: GetUser,
    private val getMajor: GetMajor,
    private val getUniversity: GetUniversity,
) : MeetingTeamGetDetailUseCase {

    @Transactional(readOnly = true)
    override fun invoke(command: MeetingTeamGetDetailUseCase.Command): MeetingTeamGetDetailUseCase.Result {
        val targetMeetingTeam = meetingTeamDomainService.getById(command.meetingId)

        val affinityScore = findMyMeetingTeam()?.let {
            meetingTeamDomainService.calculateTeamMbtiAffinityScore(it, targetMeetingTeam)
        }


        return MeetingTeamGetDetailUseCase.Result(
            meetingTeam = targetMeetingTeam,
            members = getMeetingMemberDetailInfos(targetMeetingTeam.id),
            affinityScore = affinityScore,
        )
    }

    private fun getMeetingMemberDetailInfos(meetingTeamId: UUID): List<MeetingMemberDetailInfo> {
        return meetingTeamDomainService
            .findAllMeetingMembersByMeetingTeamId(meetingTeamId)
            .map { member ->
                val user = getUser.getById(member.userId)
                MeetingMemberDetailInfo.of(
                    user = user,
                    major = getMajor.getById(user.majorId),
                    university = getUniversity.getById(user.universityId),
                    role = member.role
                )
            }
    }

    private fun findMyMeetingTeam(): MeetingTeam? {
        return getCurrentUserAuthentication()
            .let { meetingTeamDomainService.findByMemberUserId(it.userId) }

    }
}
