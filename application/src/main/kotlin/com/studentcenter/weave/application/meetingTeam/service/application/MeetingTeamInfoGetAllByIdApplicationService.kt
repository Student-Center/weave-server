package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamInfoGetAllByIdUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.application.meetingTeam.vo.MemberInfo
import com.studentcenter.weave.application.university.port.inbound.MajorGetByIdUseCase
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.university.entity.Major
import com.studentcenter.weave.domain.university.entity.University
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingTeamInfoGetAllByIdApplicationService(
    private val userQueryUseCase: UserQueryUseCase,
    private val universityGetByIdUseCase: UniversityGetByIdUsecase,
    private val majorGetByIdUseCase: MajorGetByIdUseCase,
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamInfoGetAllByIdUseCase {

    @Transactional(readOnly = true)
    override fun invoke(ids: List<UUID>): List<MeetingTeamInfo> {
        val meetingTeams = meetingTeamDomainService.getAllByIds(ids)
        // TODO(cache): cache layer 도입 시 수정 필요
        val univCache: MutableMap<UUID, University> = HashMap()
        val majorCache: MutableMap<UUID, Major> = HashMap()
        return meetingTeams.map {
            MeetingTeamInfo(team = it, memberInfos = createMemberInfos(it, univCache, majorCache))
        }
    }

    private fun createMemberInfos(
        team: MeetingTeam,
        univCache: MutableMap<UUID, University>,
        majorCache: MutableMap<UUID, Major>,
    ) : List<MemberInfo> {
        return meetingTeamDomainService
            .findAllMeetingMembersByMeetingTeamId(team.id)
            .map {
                val memberUser = userQueryUseCase.getById(it.userId)
                val university = getUniversityById(memberUser.universityId, univCache)
                val major = getMajorById(memberUser.universityId, majorCache)
                MemberInfo(
                    id = it.id,
                    user = memberUser,
                    university = university,
                    major = major,
                    role = it.role,
                )
            }
    }

    private fun getUniversityById(universityId: UUID, cache: MutableMap<UUID, University>) : University {
        if (cache.contains(universityId).not()) {
            cache[universityId] = universityGetByIdUseCase.invoke(universityId)
        }

        return cache[universityId] ?: throw NoSuchElementException("학교를 찾을 수 없습니다.")
    }

    private fun getMajorById(majorId: UUID, cache: MutableMap<UUID, Major>) : Major {
        if (cache.contains(majorId).not()) {
            cache[majorId] = majorGetByIdUseCase.invoke(majorId)
        }

        return cache[majorId] ?: throw NoSuchElementException("학과를 찾을 수 없습니다.")
    }

}
