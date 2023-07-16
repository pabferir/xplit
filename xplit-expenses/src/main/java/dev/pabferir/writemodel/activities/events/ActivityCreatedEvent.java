package dev.pabferir.writemodel.activities.events;

import dev.pabferir.writemodel.activities.valueobjects.ActivityId;
import dev.pabferir.writemodel.activities.valueobjects.Currency;

public record ActivityCreatedEvent(
        ActivityId id,
        String title,
        Currency currency) {
}
