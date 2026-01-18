package software.ulpgc.moneycalculator.model;

public record Money(long amount, Currency currency) {
    @Override
    public String toString() {
        return amount + " " + currency.symbol();
    }
}
