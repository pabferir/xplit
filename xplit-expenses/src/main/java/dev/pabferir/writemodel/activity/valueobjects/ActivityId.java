package dev.pabferir.writemodel.activity.valueobjects;

import org.axonframework.common.IdentifierFactory;

import java.io.Serializable;

public record ActivityId(
        String value) implements Serializable {


    public ActivityId() {
        this(IdentifierFactory.getInstance().generateIdentifier());
    }
}
