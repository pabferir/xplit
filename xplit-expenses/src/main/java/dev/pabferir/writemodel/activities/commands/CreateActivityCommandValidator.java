package dev.pabferir.writemodel.activities.commands;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.extensions.reactor.messaging.ReactorMessageDispatchInterceptor;
import reactor.core.publisher.Mono;

public class CreateActivityCommandValidator implements ReactorMessageDispatchInterceptor<CommandMessage<?>> {

    public Mono<CommandMessage<?>> intercept(Mono<CommandMessage<?>> message) {
        return message.flatMap(msg -> {
            if (msg.getPayload() instanceof CreateActivityCommand command) {
                if (command.id() == null) {
                    return Mono.error(new IllegalArgumentException("An activity id is required."));
                }
                if (command.title() == null || command.title().isBlank()) {
                    return Mono.error(new IllegalArgumentException("An activity title is required."));
                }
                if (command.currencyAlphaCode() == null || command.currencyAlphaCode().isBlank()) {
                    return Mono.error(new IllegalArgumentException("An activity currency is required."));
                }
            }

            return message;
        });
    }
}
