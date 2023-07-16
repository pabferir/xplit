package dev.pabferir.writemodel.activities;

import dev.pabferir.writemodel.activities.valueobjects.Money;
import dev.pabferir.writemodel.activities.valueobjects.ParticipantId;
import org.axonframework.modelling.command.EntityId;

import java.util.Objects;
import java.util.UUID;

public class Participant {
    @EntityId
    private ParticipantId id;
    private String name;
    private Money balance;

    public Participant(String name, Money balance) {
        this.id = new ParticipantId(UUID.randomUUID().toString());
        this.name = name;
        this.balance = balance;
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
                '}';
    }
}
