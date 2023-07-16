package dev.pabferir.writemodel.activities.valueobjects;

import org.axonframework.common.IdentifierFactory;

import java.io.Serializable;

public record ExpenseId(
        String value) implements Serializable {

    public ExpenseId() {
        this(IdentifierFactory.getInstance().generateIdentifier());
    }
}
