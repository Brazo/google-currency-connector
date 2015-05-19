/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.converter.currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mule.api.annotations.ConnectionStrategy;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataKeyRetriever;
import org.mule.api.annotations.MetaDataRetriever;
import org.mule.api.annotations.Processor;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.modules.converter.currency.strategy.ConnectorConnectionStrategy;

/**
 * Anypoint Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name = "GoogleCurrencyConnector", friendlyName = "Google Currency Connector")
public class GoogleCurrencyConnector {

	@ConnectionStrategy
	ConnectorConnectionStrategy connectionStrategy;

	/**
	 * Custom processor
	 * <p/>
	 * {@sample.xml ../../../doc/GoogleCurrency-connector.xml.sample
	 * googlecurrency:exchange-currency}
	 *
	 * @param fromCurrency
	 * @param toCurrency
	 * @param value
	 * @return CurrencyConverterObject
	 */
	@Processor
	public CurrencyConverterObject exchangeCurrency(String fromCurrency,
			String toCurrency, BigDecimal value) {
		// create the currency object
		CurrencyConverterObject currencyConverterObject = new CurrencyConverterObject(
				fromCurrency, toCurrency, value);
		
		// handle edge case that from currency is equal to currency
		if (fromCurrency.equals(toCurrency)) {
			currencyConverterObject.setToPrice(value);
			return currencyConverterObject;
		}
		
		URL googleCurrencyURL = currencyConverterObject.buildGoogleURL();

		// connection stuff
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) googleCurrencyURL.openConnection();
		} catch (IOException ioe) {
			new IOException("Cannot connect to Google using URL: "
					+ googleCurrencyURL.toString());
		}
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			new ProtocolException(
					"Cannot request google using GET method on URL: "
							+ googleCurrencyURL.toString());
		}

		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		String aux = "";

		try {
			br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			while ((aux = br.readLine()) != null) {
				builder.append(aux);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String html = builder.toString();

		String result[] = null;
		if (html instanceof String) {
			Document doc = Jsoup.parse(html);
			Elements links = doc.getElementsByTag("span");
			for (Element el : links) {
				result = el.text().split(" ");
			}
		}

		currencyConverterObject.setToPrice(new java.math.BigDecimal(result[0]));
		return currencyConverterObject;
	}

	public ConnectorConnectionStrategy getConnectionStrategy() {
		return connectionStrategy;
	}

	public void setConnectionStrategy(
			ConnectorConnectionStrategy connectionStrategy) {
		this.connectionStrategy = connectionStrategy;
	}
	
    @MetaDataKeyRetriever
    public List<MetaDataKey> getEntities() throws Exception {
        return CurrencyConverterObject.getMetadata();
    }

    @MetaDataRetriever
    public MetaData describeEntity(MetaDataKey entityKey) throws Exception {
        return CurrencyConverterObject.getMetaDataFromKey(entityKey);
    }
}