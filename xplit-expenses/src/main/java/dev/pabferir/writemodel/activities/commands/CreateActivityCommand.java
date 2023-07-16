package dev.pabferir.writemodel.activities.commands;

import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.UUID;

public record CreateActivityCommand(
        @TargetAggregateIdentifier
        ActivityId id,
        String title,
        String currencyAlphaCode) implements Serializable {

    public CreateActivityCommand(String title,
                                 String currencyAlphaCode) {
        this(new ActivityId(UUID.randomUUID().toString()),
             title,
             currencyAlphaCode);
    }
}
