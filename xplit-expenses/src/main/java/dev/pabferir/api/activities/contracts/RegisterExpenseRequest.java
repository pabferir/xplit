package dev.pabferir.api.activities.contracts;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RegisterExpenseRequest(
        String title,
        BigDecimal amount,
        String currencyAlphaCode,
        UUID paidBy,
        List<UUID> paidFor) {
}
