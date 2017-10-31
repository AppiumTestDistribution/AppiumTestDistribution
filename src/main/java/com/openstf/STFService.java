package com.openstf;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class creates a STF Service object.
 */
public class STFService {

    private String stfUrl;
    private String authToken;

    public STFService() {
    }

    public STFService(String stfUrl, String authToken)
            throws MalformedURLException, URISyntaxException {
        this.stfUrl = new URL(stfUrl).toURI().resolve("/api/v1/").toString();
        this.authToken = authToken;
    }

    public String getStfUrl() {
        return stfUrl;
    }

    public void setStfUrl(String stfUrl) throws MalformedURLException, URISyntaxException {
        this.stfUrl = new URL(stfUrl).toURI().resolve("/api/v1/").toString();;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
