package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.application.meetingTeam.vo.MemberInfo
import com.studentcenter.weave.domain.user.vo.KakaoId
import java.util.*

data class KakaoIdResponse(val members: List<MemberKakaoIdDto>) {

    data class MemberKakaoIdDto(
        val memberId: UUID,
        val kakaoId: KakaoId?,
    ) {
        companion object {
            fun from(memberInfo: MemberInfo) = MemberKakaoIdDto(
                memberId = memberInfo.id,
                kakaoId = memberInfo.user.kakaoId,
            )
        }
    }

    companion object {
        fun from(memberInfos: List<MemberInfo>) : KakaoIdResponse {
            return KakaoIdResponse(memberInfos.map(MemberKakaoIdDto::from))

        }
    }
}
