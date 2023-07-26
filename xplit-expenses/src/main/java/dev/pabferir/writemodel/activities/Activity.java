package dev.pabferir.writemodel.activities;

import dev.pabferir.writemodel.activities.commands.CreateActivityCommand;
import dev.pabferir.writemodel.activities.commands.EnrollParticipantsCommand;
import dev.pabferir.writemodel.activities.commands.RegisterExpenseCommand;
import dev.pabferir.writemodel.activities.events.ActivityCreatedEvent;
import dev.pabferir.writemodel.activities.events.ExpenseRegisteredEvent;
import dev.pabferir.writemodel.activities.events.ParticipantsEnrolledEvent;
import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import dev.pabferir.writemodel.activities.valueobjects.Currency;
import dev.pabferir.writemodel.activities.valueobjects.Money;
import dev.pabferir.writemodel.activities.valueobjects.ParticipantId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.*;

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
    @AggregateMember
    private final List<Expense> expenses = new ArrayList<>();

    private Activity() {
        // Needed by Axon Framework
    }

    // region Command handlers
    @CommandHandler
    Activity(CreateActivityCommand command) {
        final Optional<Currency> optionalCurrency = Currency.tryParseFromAlphaCode(command.currencyAlphaCode());
        if (optionalCurrency.isEmpty()) {
            throw new IllegalStateException("Currency code '" + command.currencyAlphaCode() + "' is not recognized.");
        }

        apply(new ActivityCreatedEvent(command.activityId(),
                                       command.title(),
                                       optionalCurrency.get()));
    }

    @CommandHandler
    void handle(EnrollParticipantsCommand command) {
        apply(new ParticipantsEnrolledEvent(command.participantNames()));
    }

    @CommandHandler
    void handle(RegisterExpenseCommand command) {
        final Optional<Currency> optionalExpenseCurrency = Currency.tryParseFromAlphaCode(command.currencyAlphaCode());
        if (optionalExpenseCurrency.isEmpty()) {
            throw new IllegalStateException("Currency code '%s' is not recognized."
                                                    .formatted(command.currencyAlphaCode()));
        }

        final Currency expenseCurrency = optionalExpenseCurrency.get();
        if (!this.currency.equals(expenseCurrency)) {
            throw new IllegalStateException("Expense currency '%s' does not match activity currency '%s'."
                                                    .formatted(command.currencyAlphaCode(),
                                                               this.currency.getAlphaCode()));
        }

        final Optional<Participant> optionalPaidBy =
                this.participants.stream()
                                 .filter(participant -> participant.getId().equals(command.paidBy()))
                                 .findFirst();
        if (optionalPaidBy.isEmpty()) {
            throw new IllegalStateException("Could not find participant with id '%s' in the activity."
                                                    .formatted(command.paidBy().value()));
        }

        command.paidFor()
               .stream()
               .filter(participantId ->
                               this.participants.stream()
                                                .noneMatch(participant -> participant.getId().equals(participantId)))
               .findAny()
               .ifPresent(participantId -> {
                   throw new IllegalStateException("Could not find participant with id '%s' in the activity."
                                                           .formatted(participantId.value()));
               });

        apply(new ExpenseRegisteredEvent(command.title(),
                                         new Money(command.amount(), expenseCurrency),
                                         command.paidBy(),
                                         Set.copyOf(command.paidFor()),
                                         command.paidOn()));
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
                    new Money(this.currency),
                    new Money(this.currency)));
        }
    }

    @EventSourcingHandler
    void on(ExpenseRegisteredEvent event) {
        this.totalCost = this.totalCost.add(event.amount());

        final Money fractionated = event.amount().fractionate(event.paidFor().size());
        this.participants.forEach(participant -> {
            final ParticipantId participantId = participant.getId();
            if (participantId.equals(event.paidBy())) {
                participant.addPaymentToBalance(event.amount());
            }
            if (event.paidFor().contains(participantId)) {
                participant.addDebtToBalance(fractionated);
                participant.addExpense(fractionated);
            }
        });

        this.expenses.add(new Expense(event.title(),
                                      event.amount(),
                                      event.paidBy(),
                                      event.paidFor(),
                                      event.paidOn()));
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

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
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
                ", expenses=" + expenses +
                '}';
    }
}
