package dev.pabferir.writemodel.activities.commands;

import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import dev.pabferir.writemodel.activities.valueobjects.ParticipantId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record RegisterExpenseCommand(
        @TargetAggregateIdentifier
        ActivityId activityId,
        String title,
        BigDecimal amount,
        String currencyAlphaCode,
        ParticipantId paidBy,
        List<ParticipantId> paidFor,
        Instant paidOn) implements Serializable {
}
