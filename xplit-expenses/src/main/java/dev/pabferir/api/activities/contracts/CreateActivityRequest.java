package dev.pabferir.api.activities.contracts;

public record CreateActivityRequest(
        String title,
        String currencyAlphaCode) {
}
