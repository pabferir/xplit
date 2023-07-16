package dev.pabferir.writemodel.activities;

import dev.pabferir.writemodel.activities.commands.CreateActivityCommand;
import dev.pabferir.writemodel.activities.commands.EnrollParticipantsCommand;
import dev.pabferir.writemodel.activities.events.ActivityCreatedEvent;
import dev.pabferir.writemodel.activities.events.ParticipantsEnrolledEvent;
import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import dev.pabferir.writemodel.activities.valueobjects.Currency;
import dev.pabferir.writemodel.activities.valueobjects.Money;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    Activity(CreateActivityCommand command) {
        Optional<Currency> optionalCurrency = Currency.tryParseFromAlphaCode(command.currencyAlphaCode());
        if (optionalCurrency.isEmpty()) {
            throw new IllegalStateException("Provided currency is not recognized.");
        }

        apply(new ActivityCreatedEvent(
                command.id(),
                command.title(),
                optionalCurrency.get()));
    }

    @CommandHandler
    void handle(EnrollParticipantsCommand command) {
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

    public ActivityId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public List<Participant> getParticipants() {
        return List.copyOf(participants);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity activity)) return false;
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", currency=" + currency +
                ", totalCost=" + totalCost +
                ", participants=" + participants +
                '}';
    }
}
