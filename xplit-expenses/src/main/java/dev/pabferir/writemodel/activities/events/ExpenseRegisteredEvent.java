package dev.pabferir.writemodel.activities.events;

import dev.pabferir.writemodel.activities.valueobjects.Money;
import dev.pabferir.writemodel.activities.valueobjects.ParticipantId;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

public record ExpenseRegisteredEvent(
        String title,
        Money amount,
        ParticipantId paidBy,
        Set<ParticipantId> paidFor,
        Instant paidOn) implements Serializable {
}
