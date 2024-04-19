package com.studentcenter.weave.infrastructure.client.discord.meeting.adaptor

import com.studentcenter.weave.application.meeting.port.outbound.MeetingEventMessagePort
import com.studentcenter.weave.application.meeting.vo.MeetingMatchingEvent
import com.studentcenter.weave.infrastructure.client.common.event.EventType
import com.studentcenter.weave.infrastructure.client.common.properties.ClientProperties
import com.studentcenter.weave.infrastructure.client.discord.common.vo.DiscordMessage
import com.studentcenter.weave.infrastructure.client.discord.util.DiscordClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.net.URI

@Component
class MeetingEventMessageDiscordAdaptor(
    val discordClient: DiscordClient,
    val clientProperties: ClientProperties,
) : MeetingEventMessagePort {

    private val logger = KotlinLogging.logger {}

    override fun sendMeetingIsMatchedMessage(meetingMatchingEvent: MeetingMatchingEvent) {
        if (this.clientProperties.events.getValue(EventType.MEETING_MATCHING).active) {
            val meetingTeamMemberCount: Int = meetingMatchingEvent.memberCount / 2
            val discordUri =
                URI(this.clientProperties.events.getValue(EventType.MEETING_MATCHING).url)
            val prefix = if (meetingMatchingEvent.matchedMeetingCount % 10 == 0) "@everyone " else ""
            val message =
                "$prefix${meetingMatchingEvent.matchedMeetingCount}번째 미팅 " +
                        "${meetingMatchingEvent.meeting.requestingTeamId}(${meetingMatchingEvent.requestingMeetingTeamMbti.value})와 " +
                        "${meetingMatchingEvent.meeting.receivingTeamId}(${meetingMatchingEvent.receivingMeetingTeamMbti.value})의 " +
                        "${meetingTeamMemberCount}:${meetingTeamMemberCount} 미팅이 성사되었어요!"

            runCatching {
                discordClient.send(
                    uri = discordUri,
                    message = DiscordMessage(message),
                )
            }.onFailure {
                logger.error(it) { "Failed to send discord message" }
            }
        }
    }
}
