package software.ulpgc.moneycalculator;

import software.ulpgc.moneycalculator.io.CurrencyLoader;
import software.ulpgc.moneycalculator.io.ExchangeRateLoader;
import software.ulpgc.moneycalculator.io.WebServiceCurrencyLoader;
import software.ulpgc.moneycalculator.io.WebServiceExchangeRateLoader;
import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.model.ExchangeRate;
import software.ulpgc.moneycalculator.model.Money;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        CurrencyLoader currencyLoader = new WebServiceCurrencyLoader();
        List<Currency> currencies = currencyLoader.loadAll();

        Currency euro = currencies.stream().filter(c -> c.symbol().equals("EUR")).findFirst().orElseThrow();
        Currency dollar = currencies.stream().filter(c -> c.symbol().equals("USD")).findFirst().orElseThrow();

        ExchangeRateLoader exchangeRateLoader = new WebServiceExchangeRateLoader();
        ExchangeRate exchangeRate = exchangeRateLoader.load(euro, dollar);

        Money originalMoney = new Money(100, euro);
        Money convertedMoney = new Money((long) (originalMoney.amount() * exchangeRate.rate()), dollar);

        System.out.println("Converting " + originalMoney);
        System.out.println("To " + dollar.symbol());
        System.out.println("Rate: " + exchangeRate.rate());
        System.out.println("Result: " + convertedMoney);
    }
}
