package software.ulpgc.moneycalculator.io;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.model.ExchangeRate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;

public class WebServiceExchangeRateLoader implements ExchangeRateLoader {
    private static final String ApiKey = "aeb1cd5ef6081142040d717f";
    private static final String UrlTemplate = "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s";

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            return loadExchangeRate(from, to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ExchangeRate loadExchangeRate(Currency from, Currency to) throws IOException {
        String json = loadJson(from, to);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        return new ExchangeRate(from, to, LocalDate.now(), jsonObject.get("conversion_rate").getAsDouble());
    }

    private String loadJson(Currency from, Currency to) throws IOException {
        URL url = URI.create(String.format(UrlTemplate, ApiKey, from.symbol(), to.symbol())).toURL();
        try (InputStream is = url.openStream()) {
            return new String(is.readAllBytes());
        }
    }
}
