package com.studentcenter.weave.infrastructure.client.discord.meeting.adaptor

import com.studentcenter.weave.application.meeting.port.outbound.MeetingEventPort
import com.studentcenter.weave.application.meeting.vo.MeetingMatchingInfo
import com.studentcenter.weave.infrastructure.client.common.event.ClientEventType
import com.studentcenter.weave.infrastructure.client.common.properties.ClientProperties
import com.studentcenter.weave.infrastructure.client.discord.common.vo.DiscordMessage
import com.studentcenter.weave.infrastructure.client.discord.util.DiscordClient
import org.springframework.stereotype.Component
import java.net.URI

@Component
class MeetingEventDiscordAdaptor(
    val discordClient: DiscordClient,
    val clientProperties: ClientProperties,
) : MeetingEventPort {

    override fun sendMeetingIsMatchedMessage(meetingMatchingInfo: MeetingMatchingInfo) {
        if (this.clientProperties.events.getValue(ClientEventType.MEETING_MATCHING).active) {
            val meetingTeamMemberCount: Int = meetingMatchingInfo.memberCount / 2
            val discordUri =
                URI(this.clientProperties.events.getValue(ClientEventType.MEETING_MATCHING).url)
            val message =
                "${meetingMatchingInfo.matchedMeetingCount}번째 미팅 " +
                        "${meetingMatchingInfo.meeting.requestingTeamId}(${meetingMatchingInfo.requestingMeetingTeamMbti.value})와 " +
                        "${meetingMatchingInfo.meeting.receivingTeamId}(${meetingMatchingInfo.receivingMeetingTeamMbti.value})의 " +
                        "${meetingTeamMemberCount}:${meetingTeamMemberCount} 미팅이 성사되었어요!"

            runCatching {
                discordClient.send(
                    uri = discordUri,
                    message = DiscordMessage(message),
                )
            }.onFailure {
                // TODO: 로깅 시스템 도입 시 로그 추가.
            }
        }
    }
}
