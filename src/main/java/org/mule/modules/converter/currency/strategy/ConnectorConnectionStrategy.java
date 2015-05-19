/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.converter.currency.strategy;

import java.net.MalformedURLException;
import java.net.URL;

import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;

/**
 * Configuration type Strategy
 *
 * @author MuleSoft, Inc.
 */

@ConnectionManagement(configElementName = "config-type", 
  friendlyName = "No auth strategy")
public class ConnectorConnectionStrategy {

    /**
     * Connect
     *
     * @param username A username
     * @param password A password
     * @throws ConnectionException
     */
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String username, @Password String password)
            throws ConnectionException {
        try {
            URL connectURL = new URL("http", "www.google.com", "finance/converter");
        } catch (MalformedURLException mue) {
            throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "-", "Can't reach google currency converter" + mue.getMessage());
        }
        /*
         * CODE FOR ESTABLISHING A CONNECTION GOES HERE
         */
    }
    
    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        /*
         * CODE FOR CLOSING A CONNECTION GOES HERE
         */
    }

    /**
     * Are we connected
     */
    @ValidateConnection
    public boolean isConnected() {
        try {
            URL connectURL = new URL("http", "www.google.com", "finance/converter");
        } catch (MalformedURLException mue) {
            return false;
        }
        return true;
    }

    /**
     * Connection identifier
     */
    @ConnectionIdentifier
    public String connectionId() {
        return "001";
    }

}