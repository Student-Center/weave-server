package com.studentcenter.weave.application.chat.vo

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.chat.entity.ChatRoom
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

/**
 * 채팅방에 참여중인 멤버만 채팅방 상세 정보를 조회할 수 있습니다.
 */
data class ChatRoomDetail(
    val id: UUID,
    val myTeam: Team,
    val otherTeam: Team,
) {

    data class Team(
        val meetingTeamId: UUID,
        val name: String,
        val members: List<Member>,
    ) {

        data class Member(
            val userId: UUID,
            val name: String,
            val avatar: Url?,
        )
    }

    companion object {

        fun ChatRoom.toDetail(
            getUser: GetUser,
            getMeetingTeam: GetMeetingTeam,
        ): ChatRoomDetail {
            val currentUserId: UUID = getCurrentUserAuthentication().userId

            val (myTeam, otherTeam) = getTeams(currentUserId, getMeetingTeam)

            return ChatRoomDetail(
                id = this.id,
                myTeam = mapTeamToDetailTeam(myTeam, getUser),
                otherTeam = mapTeamToDetailTeam(otherTeam, getUser)
            )
        }

        private fun ChatRoom.getTeams(
            currentUserId: UUID,
            getMeetingTeam: GetMeetingTeam,
        ): Pair<MeetingTeam, MeetingTeam> {
            val requestingTeam: MeetingTeam = getMeetingTeam.getById(this.requestingTeamId)
            val receivingTeam: MeetingTeam = getMeetingTeam.getById(this.receivingTeamId)

            return when {
                requestingTeam.isMyTeam(currentUserId) -> requestingTeam to receivingTeam
                receivingTeam.isMyTeam(currentUserId) -> receivingTeam to requestingTeam
                else -> throw IllegalArgumentException("채팅방에 소속된 사용자가 아닙니다.")
            }
        }

        private fun mapTeamToDetailTeam(
            team: MeetingTeam,
            getUser: GetUser,
        ): Team {
            return Team(
                meetingTeamId = team.id,
                name = team.teamIntroduce.value,
                members = team
                    .members
                    .map { it.userId }
                    .let { mapMembersToDetailMembers(it, getUser) }
            )
        }

        private fun mapMembersToDetailMembers(
            userIds: List<UUID>,
            getUser: GetUser,
        ): List<Team.Member> {
            return getUser
                .findAllByIds(userIds)
                .map { user ->
                    Team.Member(
                        userId = user.id,
                        name = user.nickname.value,
                        avatar = user.avatar
                    )
                }

        }
    }

}
