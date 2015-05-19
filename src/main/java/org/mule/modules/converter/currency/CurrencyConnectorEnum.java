package org.mule.modules.converter.currency;

/**
 * Created by pat on 10/06/14.
 */
public enum CurrencyConnectorEnum {

    CURRENCY_OBJECT("Currency Object");

    private final String displayName;

    CurrencyConnectorEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
