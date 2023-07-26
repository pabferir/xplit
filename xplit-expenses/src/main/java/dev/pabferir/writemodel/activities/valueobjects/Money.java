package dev.pabferir.writemodel.activities.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public record Money(
        BigDecimal amount,
        Currency currency) implements Serializable {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public Money(Currency currency) {
        this(BigDecimal.ZERO, currency);
    }

    public Money add(Money amount) {
        BigDecimal addition = this.amount.add(amount.amount(),
                                              new MathContext(SCALE, ROUNDING_MODE));

        return new Money(addition,
                         this.currency);
    }

    public Money subtract(Money amount) {
        BigDecimal addition = this.amount.subtract(amount.amount(),
                                                   new MathContext(SCALE, ROUNDING_MODE));

        return new Money(addition,
                         this.currency);
    }

    public Money fractionate(int parts) {
        BigDecimal fractionatedAmount =
                this.amount.divide(BigDecimal.valueOf(parts), SCALE, ROUNDING_MODE);

        return new Money(fractionatedAmount,
                         this.currency);
    }
}
