package com.studentcenter.weave.domain.meeting.exception

import com.studentcenter.weave.support.common.exception.CustomException

sealed class MeetingException(
    codeNumber: Int,
    message: String,
) : CustomException(CODE_PREFIX, codeNumber, message) {

    class AlreadyFinished(message: String = "이미 종료된 미팅입니다.") :
        MeetingException(codeNumber = 1, message = message)

    class NotJoinedUser(message: String = "미팅에 참여하지 않는 유저입니다") :
        MeetingException(codeNumber = 2, message = message)

    class AlreadyAttended(message: String = "이미 미팅 참여 의사를 결정했습니다.") :
        MeetingException(codeNumber = 3, message = message)

    class UniversityMailUnverifiedUser(message: String = "대학교 이메일 인증이 되지 않았어요! 대학교 이메일을 인증해 주세요!") :
        MeetingException(codeNumber = 4, message = message)

    class CanNotRequestToSameGender(message: String = "다른 성별의 미팅팀에만 미팅을 요청할 수 있어요!") :
        MeetingException(codeNumber = 5, message = message)

    class CanNotRequestToDifferentMemberCount(message: String = "동일한 인원수의 미팅팀에만 미팅을 요청할 수 있어요!") :
        MeetingException(codeNumber = 6, message = message)

    class AlreadyRequestMeeting(message: String = "이미 상대팀에게 미팅을 신청했어요!") :
        MeetingException(codeNumber = 7, message = message)

    class IsNotCompletedMeeting(message: String = "완료된 미팅이 아닙니다.") :
        MeetingException(codeNumber = 8, message = message)

    companion object {
        const val CODE_PREFIX = "MEETING"
    }

}
