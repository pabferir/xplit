package dev.pabferir.writemodel.activities.events;

import java.util.List;

public record ParticipantsEnrolledEvent(
        List<String> participantNames) {
}
