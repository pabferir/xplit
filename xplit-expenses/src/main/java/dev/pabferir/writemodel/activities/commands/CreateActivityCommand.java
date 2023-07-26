package dev.pabferir.writemodel.activities.commands;

import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

public record CreateActivityCommand(
        @TargetAggregateIdentifier
        ActivityId activityId,
        String title,
        String currencyAlphaCode) implements Serializable {

    public CreateActivityCommand(String title,
                                 String currencyAlphaCode) {
        this(new ActivityId(),
             title,
             currencyAlphaCode);
    }
}
