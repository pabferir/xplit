package dev.pabferir.writemodel.activities;

import dev.pabferir.writemodel.activities.valueobjects.ExpenseId;
import dev.pabferir.writemodel.activities.valueobjects.Money;
import dev.pabferir.writemodel.activities.valueobjects.ParticipantId;
import org.axonframework.modelling.command.EntityId;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public class Expense {
    @EntityId
    private ExpenseId id;
    private String title;
    private Money amount;
    private ParticipantId paidBy;
    private Set<ParticipantId> paidFor;
    private Instant paidOn;

    public Expense(String title, Money amount, ParticipantId paidBy, Set<ParticipantId> paidFor, Instant paidOn) {
        this.id = new ExpenseId();
        this.title = title;
        this.amount = amount;
        this.paidBy = paidBy;
        this.paidFor = paidFor;
        this.paidOn = paidOn;
    }

    public ExpenseId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Money getAmount() {
        return amount;
    }

    public ParticipantId getPaidBy() {
        return paidBy;
    }

    public Set<ParticipantId> getPaidFor() {
        return Set.copyOf(paidFor);
    }

    public Instant getPaidOn() {
        return paidOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense expense)) return false;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
