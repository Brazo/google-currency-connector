
Mulemodulecurrency
=========================
Use this connector to convert one currency to another.
It will connect to https://www.google.com/finance/converter providing the following parameters:

1. Price value to be converted (BigDecimal)

2. From currency (String) - it uses the abbrevations given on the google side (e.g. EUR, USD, AED, ...)

3. To currency (String) - the desired conversion value

The result the connector will spit out is a Pojo having the following attributes:

```java
public final class CurrencyConverterObject {

    private final String fromCurrency;
    private final String toCurrency;
    private final BigDecimal fromPrice;
    private BigDecimal toPrice;

    ...
}
```

![Currency Converter Config](screenshot.png?raw=true "Currency Converter Config")


Installation and Usage
----------------------


Building the project
```bash
mvn clean package -Ddevkit.studio.package.skip=false
```
Installing Locally
```bash
mvn org.apache.maven.plugins:maven-install-plugin:2.5.1:install-file  -Dfile=/Users/pat/Documents/source/mule-module-currency/target/mule-module-currency-1.0-SNAPSHOT.jar -DgroupId=org.mule.module -DartifactId=mule-module-currency -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DlocalRepositoryPath=/Users/pat/.m2/repository/
```

For to run this connector in a complete Mule Maven application.

Be sure you add this dependency to the application using the connector:
```xml
        <dependency>
            <!-- jsoup HTML parser library @ http://jsoup.org/ -->
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.7.3</version>
        </dependency>
```

Also ensure the following inclusion is in your pom:
```xml
		<plugins>
			<plugin>
				<groupId>org.mule.tools</groupId>
				<artifactId>maven-mule-plugin</artifactId>
				<version>1.9</version>
				<extensions>true</extensions>
				<configuration>
					<copyToAppsDirectory>true</copyToAppsDirectory>
					<inclusions>
                        <inclusion>
                     		<groupId>org.mule.module</groupId>
							<artifactId>mule-module-currency</artifactId>
                        </inclusion>
                    </inclusions>
				</configuration>
			</plugin>
```

