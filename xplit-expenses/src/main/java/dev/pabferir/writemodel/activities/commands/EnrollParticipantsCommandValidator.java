package dev.pabferir.writemodel.activities.commands;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.extensions.reactor.messaging.ReactorMessageDispatchInterceptor;
import reactor.core.publisher.Mono;

public final class EnrollParticipantsCommandValidator implements ReactorMessageDispatchInterceptor<CommandMessage<?>> {

    public Mono<CommandMessage<?>> intercept(Mono<CommandMessage<?>> message) {
        return message.flatMap(msg -> {
            if (msg.getPayload() instanceof EnrollParticipantsCommand command) {
                try {
                    validate(command);
                } catch (IllegalArgumentException e) {
                    return Mono.error(e);
                }
            }

            return message;
        });
    }

    private static void validate(EnrollParticipantsCommand command) {
        if (command.activityId() == null) {
            throw new IllegalArgumentException("An activity id is required.");
        }

        if (command.participantNames() == null ||
                command.participantNames().isEmpty() ||
                command.participantNames().stream().anyMatch(String::isBlank)) {
            throw new IllegalArgumentException("Participant names are required.");
        }
    }

}
