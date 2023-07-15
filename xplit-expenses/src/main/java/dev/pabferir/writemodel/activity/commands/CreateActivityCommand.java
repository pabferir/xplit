package dev.pabferir.writemodel.activity.commands;

import dev.pabferir.writemodel.activity.valueobjects.ActivityId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.UUID;

public record CreateActivityCommand(
        @TargetAggregateIdentifier @NotNull
        ActivityId id,
        @NotBlank
        String title,
        @NotBlank
        String currencyAlphaCode) implements Serializable {

    public CreateActivityCommand(@NotBlank
                                 String title,
                                 @NotBlank
                                 String currencyAlphaCode) {
        this(new ActivityId(UUID.randomUUID().toString()),
             title,
             currencyAlphaCode);
    }
}
