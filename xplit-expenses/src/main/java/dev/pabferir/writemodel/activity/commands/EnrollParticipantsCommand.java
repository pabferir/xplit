package dev.pabferir.writemodel.activity.commands;

import dev.pabferir.writemodel.activity.valueobjects.ActivityId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.List;

public record EnrollParticipantsCommand(
        @TargetAggregateIdentifier @NotNull
        ActivityId activityId,
        @NotEmpty
        List<@NotBlank String> participantNames) implements Serializable {
}
