package dev.pabferir.writemodel.activities.valueobjects;

import java.io.Serializable;
import java.util.Optional;

public enum Currency implements Serializable {
    AUD("Australian Dollar", "AUD", "036", "A$", '$', SymbolNotation.AFTER),
    CHD("Swiss Franc", "CHD", "756", "Fr.", '₣', SymbolNotation.AFTER),
    CND("Canadian Dollar", "CND", "124", "C$", '$', SymbolNotation.AFTER),
    CNY("Yuan Renminbi", "CNY", "156", "¥", '¥', SymbolNotation.BEFORE),
    EUR("Euro", "EUR", "978", "€", '€', SymbolNotation.AFTER),
    GBP("Pound Sterling", "GBP", "826", "£", '£', SymbolNotation.BEFORE),
    HKD("Hong Kong Dollar", "HKD", "344", "HK$", '$', SymbolNotation.BEFORE),
    JPY("Yen", "JPY", "392", "¥", '¥', SymbolNotation.BEFORE),
    SGD("Singapore Dollar", "SGD", "702", "S$", '$', SymbolNotation.BEFORE),
    USD("US Dollar", "USD", "840", "US$", '$', SymbolNotation.BEFORE),
    ;

    private final String name;
    private final String alphaCode;
    private final String numCode;
    private final String symbol;
    private final Character glyph;
    private final SymbolNotation symbolNotation;

    Currency(String name, String alphaCode, String numCode, String symbol, Character glyph,
             SymbolNotation symbolNotation) {
        this.name = name;
        this.alphaCode = alphaCode;
        this.numCode = numCode;
        this.symbol = symbol;
        this.glyph = glyph;
        this.symbolNotation = symbolNotation;
    }

    public static Optional<Currency> tryParseFromAlphaCode(String alphaCode) {
        alphaCode = alphaCode.toUpperCase();

        for (Currency currency : values()) {
            if (currency.name().equals(alphaCode)) {
                return Optional.of(currency);
            }
        }

        return Optional.empty();
    }

    public String getName() {
        return name;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public String getNumCode() {
        return numCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public Character getGlyph() {
        return glyph;
    }

    public SymbolNotation getSymbolNotation() {
        return symbolNotation;
    }

    public enum SymbolNotation implements Serializable {
        BEFORE,
        BETWEEN,
        AFTER;
    }
}
