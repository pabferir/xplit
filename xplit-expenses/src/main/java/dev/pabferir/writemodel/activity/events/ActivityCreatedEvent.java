package dev.pabferir.writemodel.activity.events;

import dev.pabferir.writemodel.activity.valueobjects.ActivityId;
import dev.pabferir.writemodel.activity.valueobjects.Currency;

public record ActivityCreatedEvent(
        ActivityId id,
        String title,
        Currency currency) {
}
