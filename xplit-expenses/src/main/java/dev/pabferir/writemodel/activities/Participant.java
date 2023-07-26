package dev.pabferir.writemodel.activities;

import dev.pabferir.writemodel.activities.valueobjects.Money;
import dev.pabferir.writemodel.activities.valueobjects.ParticipantId;
import org.axonframework.modelling.command.EntityId;

import java.util.Objects;

public class Participant {
    @EntityId
    private ParticipantId id;
    private String name;
    private Money balance;
    private Money totalExpense;

    public Participant(String name, Money balance, Money totalExpense) {
        this.id = new ParticipantId();
        this.name = name;
        this.balance = balance;
        this.totalExpense = totalExpense;
    }

    void addPaymentToBalance(Money payment) {
        this.balance = this.balance.add(payment);
    }

    void addDebtToBalance(Money payment) {
        this.balance = this.balance.subtract(payment);
    }

    void addExpense(Money expense) {
        this.totalExpense = this.totalExpense.add(expense);
    }

    public ParticipantId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getBalance() {
        return balance;
    }

    public Money getTotalExpense() {
        return totalExpense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participant that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", totalExpense=" + totalExpense +
                '}';
    }
}
