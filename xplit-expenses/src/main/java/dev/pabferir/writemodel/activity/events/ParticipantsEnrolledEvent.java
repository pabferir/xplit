package dev.pabferir.writemodel.activity.events;

import java.util.List;

public record ParticipantsEnrolledEvent(
        List<String> participantNames) {
}
