package org.mule.modules.converter.currency;

import org.mule.common.metadata.*;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pat on 07/06/14.
 * Immutable object that only should be updated with the price.
 */
public final class CurrencyConverterObject {

    private final String fromCurrency;
    private final String toCurrency;
    private final BigDecimal fromPrice;
    private BigDecimal toPrice;

    CurrencyConverterObject(String fromCurrency, String toCurrency, BigDecimal fromPrice) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromPrice = fromPrice;
    }

    static List<MetaDataKey> getMetadata() {
        List<MetaDataKey> entities = new ArrayList<MetaDataKey>();
        entities.add(new DefaultMetaDataKey(CurrencyConnectorEnum.CURRENCY_OBJECT.name(), CurrencyConnectorEnum.CURRENCY_OBJECT.getDisplayName()));
        return entities;
    }

    static MetaData getMetaDataFromKey(MetaDataKey entityKey) {
        if (CurrencyConnectorEnum.CURRENCY_OBJECT.name().equals(entityKey.getId())) {
            MetaDataModel currencyModel = new DefaultMetaDataBuilder().createPojo(CurrencyConverterObject.class).build();
            return new DefaultMetaData(currencyModel);
        }
        throw new RuntimeException(String.format("This entity %s is not supported", entityKey.getId()));
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public BigDecimal getFromPrice() {
        return fromPrice;
    }

    public BigDecimal getToPrice() {
        return toPrice;
    }

    public void setToPrice(BigDecimal toPrice) {
        this.toPrice = toPrice;
    }

    public URL buildGoogleURL() {
        URL googleCurrencyURL = null;

        StringBuilder urlString = new StringBuilder()
                .append("http://www.google.com/finance/converter?a=")
                .append(getFromPrice().toString())
                .append("&from=")
                .append(getFromCurrency())
                .append("&to=")
                .append(getToCurrency())
                .append("&meta=ei%3DPNVzU7DsKoj6wAPkrIGYDQ");

        try {
            googleCurrencyURL = new URL(urlString.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return googleCurrencyURL;
    }
}
