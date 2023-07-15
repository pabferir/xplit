package dev.pabferir.writemodel.activity;

import dev.pabferir.writemodel.activity.commands.CreateActivityCommand;
import dev.pabferir.writemodel.activity.commands.EnrollParticipantsCommand;
import dev.pabferir.writemodel.activity.events.ActivityCreatedEvent;
import dev.pabferir.writemodel.activity.events.ParticipantsEnrolledEvent;
import dev.pabferir.writemodel.activity.valueobjects.ActivityId;
import dev.pabferir.writemodel.activity.valueobjects.Currency;
import dev.pabferir.writemodel.activity.valueobjects.Money;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Activity {
    @AggregateIdentifier
    private ActivityId id;
    private String title;
    private Currency currency;
    private Money totalCost;
    @AggregateMember
    private final List<Participant> participants = new ArrayList<>();

    private Activity() {
        // Needed by Axon Framework
    }

    // region Command handlers
    @CommandHandler
    Activity(@Valid CreateActivityCommand command) {
        Optional<Currency> optionalCurrency = Currency.tryParseFromAlphaCode(command.currencyAlphaCode());
        if (optionalCurrency.isEmpty()) {
            throw new IllegalStateException("Provided Currency is not recognized.");
        }

        apply(new ActivityCreatedEvent(
                command.id(),
                command.title(),
                optionalCurrency.get()));
    }

    @CommandHandler
    void handle(@Valid EnrollParticipantsCommand command) {
        apply(new ParticipantsEnrolledEvent(command.participantNames()));
    }

    //endregion

    // region Event sourcing handlers
    @EventSourcingHandler
    void on(ActivityCreatedEvent event) {
        this.id = event.id();
        this.title = event.title();
        this.currency = event.currency();
        this.totalCost = new Money(this.currency);
    }

    @EventSourcingHandler
    void on(ParticipantsEnrolledEvent event) {
        for (String participantName : event.participantNames()) {
            this.participants.add(new Participant(
                    participantName,
                    new Money(this.currency)
            ));
        }
    }

    // endregion
}
