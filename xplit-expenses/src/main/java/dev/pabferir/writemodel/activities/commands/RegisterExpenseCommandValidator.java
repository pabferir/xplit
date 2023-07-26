package dev.pabferir.writemodel.activities.commands;

import dev.pabferir.writemodel.activities.valueobjects.ParticipantId;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.extensions.reactor.messaging.ReactorMessageDispatchInterceptor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public final class RegisterExpenseCommandValidator implements ReactorMessageDispatchInterceptor<CommandMessage<?>> {

    public Mono<CommandMessage<?>> intercept(Mono<CommandMessage<?>> message) {
        return message.flatMap(msg -> {
            if (msg.getPayload() instanceof RegisterExpenseCommand command) {
                try {
                    validate(command);
                } catch (IllegalArgumentException e) {
                    return Mono.error(e);
                }
            }

            return message;
        });
    }

    private static void validate(RegisterExpenseCommand command) {
        if (command.activityId() == null) {
            throw new IllegalArgumentException("An activity id is required.");
        }

        if (command.title() == null ||
                command.title().isBlank()) {
            throw new IllegalArgumentException("An expense title is required.");
        }

        if (command.amount() == null ||
                command.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("An expense amount is required.");
        }

        if (command.currencyAlphaCode() == null ||
                command.currencyAlphaCode().isBlank()) {
            throw new IllegalArgumentException("An expense currency is required.");
        }

        if (command.paidBy() == null) {
            throw new IllegalArgumentException("An expense payer is required.");
        }

        if (command.paidFor() == null ||
                command.paidFor().isEmpty()) {
            throw new IllegalArgumentException("At least an expense receiver is required.");
        }

        final Set<ParticipantId> paidFor = new HashSet<>();
        command.paidFor().forEach(participantId -> {
            if (!paidFor.add(participantId)) {
                throw new IllegalArgumentException("Repeated participants are not allowed in expense recipients.");
            }
        });

        if (command.paidOn() == null) {
            throw new IllegalArgumentException("An expense date is required.");
        }
    }
}
