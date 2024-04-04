package com.studentcenter.weave.bootstrap.chat.dto

import com.studentcenter.weave.application.chat.vo.ChatRoomDetail
import java.util.*

data class ChatRoomDetailResponse(
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
            val nickname: String,
            val avatar: String?,
        )
    }


    companion object {

        fun ChatRoomDetail.toResponse(): ChatRoomDetailResponse {
            return ChatRoomDetailResponse(
                id = this.id,
                myTeam = Team(
                    meetingTeamId = this.myTeam.meetingTeamId,
                    name = this.myTeam.name,
                    members = this.myTeam.members.map {
                        Team.Member(
                            userId = it.userId,
                            nickname = it.name,
                            avatar = it.avatar?.value,
                        )
                    }
                ),
                otherTeam = Team(
                    meetingTeamId = this.otherTeam.meetingTeamId,
                    name = this.otherTeam.name,
                    members = this.otherTeam.members.map {
                        Team.Member(
                            userId = it.userId,
                            nickname = it.name,
                            avatar = it.avatar?.value,
                        )
                    }
                )
            )
        }
    }

}
