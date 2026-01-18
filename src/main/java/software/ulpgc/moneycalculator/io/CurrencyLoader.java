package software.ulpgc.moneycalculator.io;

import software.ulpgc.moneycalculator.model.Currency;

import java.util.List;

public interface CurrencyLoader {
    List<Currency> loadAll();
}
