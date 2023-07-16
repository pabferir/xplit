package dev.pabferir.writemodel.activities.valueobjects;

import org.axonframework.common.IdentifierFactory;

import java.io.Serializable;

public record ParticipantId(
        String value) implements Serializable {

    public ParticipantId() {
        this(IdentifierFactory.getInstance().generateIdentifier());
    }
}
