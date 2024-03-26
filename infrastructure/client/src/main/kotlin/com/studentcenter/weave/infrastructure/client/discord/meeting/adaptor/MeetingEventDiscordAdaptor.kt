package com.studentcenter.weave.infrastructure.client.discord.meeting.adaptor

import com.studentcenter.weave.application.meeting.port.outbound.MeetingEventPort
import com.studentcenter.weave.application.meeting.vo.MeetingMatchingEvent
import com.studentcenter.weave.infrastructure.client.common.event.EventType
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

    override fun sendMeetingIsMatchedMessage(meetingMatchingEvent: MeetingMatchingEvent) {
        if (this.clientProperties.events.getValue(EventType.MEETING_MATCHING).active) {
            val meetingTeamMemberCount: Int = meetingMatchingEvent.memberCount / 2
            val discordUri =
                URI(this.clientProperties.events.getValue(EventType.MEETING_MATCHING).url)
            val message =
                "${meetingMatchingEvent.matchedMeetingCount}번째 미팅 " +
                        "${meetingMatchingEvent.meeting.requestingTeamId}(${meetingMatchingEvent.requestingMeetingTeamMbti.value})와 " +
                        "${meetingMatchingEvent.meeting.receivingTeamId}(${meetingMatchingEvent.receivingMeetingTeamMbti.value})의 " +
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
