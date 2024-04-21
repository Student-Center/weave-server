package com.studentcenter.weave.domain.meetingTeam.exception

import com.studentcenter.weave.support.common.exception.CustomException

sealed class MeetingTeamException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class IsNotTeamMember(message: String = "팀 멤버가 아닙니다.") :
        MeetingTeamException(codeNumber = 1, message = message)

    class CanNotFindMyMeetingTeam(message: String = "내 미팅팀이 존재하지 않아요! 미팅팀에 참여해 주세요!") :
        MeetingTeamException(codeNumber = 2, message = message)

    class InvitationCodeNotFound(message: String = "초대 링크를 찾을 수 없어요! 새로운 초대 링크를 통해 입장해 주세요!") :
        MeetingTeamException(codeNumber = 3, message = message)

    class IsNotPublishedTeam(message: String = "내 미팅팀이 아직 공개되지 않았어요!") :
        MeetingTeamException(codeNumber = 4, message = message)

    companion object {
        const val CODE_PREFIX = "MEETING-TEAM"
    }

}
