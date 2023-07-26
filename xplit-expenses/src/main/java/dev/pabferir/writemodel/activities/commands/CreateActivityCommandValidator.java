package dev.pabferir.writemodel.activities.commands;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.extensions.reactor.messaging.ReactorMessageDispatchInterceptor;
import reactor.core.publisher.Mono;

public final class CreateActivityCommandValidator implements ReactorMessageDispatchInterceptor<CommandMessage<?>> {

    public Mono<CommandMessage<?>> intercept(Mono<CommandMessage<?>> message) {
        return message.flatMap(msg -> {
            if (msg.getPayload() instanceof CreateActivityCommand command) {
                try {
                    validate(command);
                } catch (IllegalArgumentException e) {
                    return Mono.error(e);
                }
            }

            return message;
        });
    }

    private static void validate(CreateActivityCommand command) {
        if (command.activityId() == null) {
            throw new IllegalArgumentException("An activity id is required.");
        }

        if (command.title() == null ||
                command.title().isBlank()) {
            throw new IllegalArgumentException("An activity title is required.");
        }

        if (command.currencyAlphaCode() == null ||
                command.currencyAlphaCode().isBlank()) {
            throw new IllegalArgumentException("An activity currency is required.");
        }
    }
}
