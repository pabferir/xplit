package dev.pabferir.writemodel.activities.commands;

import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.List;

public record EnrollParticipantsCommand(
        @TargetAggregateIdentifier
        ActivityId activityId,
        List<String> participantNames) implements Serializable {
}
