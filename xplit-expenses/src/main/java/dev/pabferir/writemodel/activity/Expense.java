package dev.pabferir.writemodel.activity;

import dev.pabferir.writemodel.activity.valueobjects.ExpenseId;
import dev.pabferir.writemodel.activity.valueobjects.ParticipantId;
import org.axonframework.modelling.command.EntityId;

import java.time.Instant;
import java.util.Set;

public class Expense {
    @EntityId
    private ExpenseId id;
    private ParticipantId paidBy;
    private Set<ParticipantId> paidFor;
    private Instant paidOn;

}
