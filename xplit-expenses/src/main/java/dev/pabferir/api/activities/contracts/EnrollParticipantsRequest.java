package dev.pabferir.api.activities.contracts;

import java.util.List;

public record EnrollParticipantsRequest(
        List<String> participantNames) {
}
