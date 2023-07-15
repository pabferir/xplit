package dev.pabferir.writemodel.activity.valueobjects;

import java.math.BigDecimal;

public record Money(
        BigDecimal amount,
        Currency currency) {

    public Money(Currency currency) {
        this(BigDecimal.ZERO, currency);
    }
}
