package com.appium.utils;


import org.apache.commons.io.IOUtils;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;


public class Api extends Helpers {

    public String getResponse(String url) {
        String body;
        try {
            HttpClient client = HttpClient.Factory.createDefault().createClient(new URL(url));
            HttpRequest request = new HttpRequest(HttpMethod.GET, url);
            request.addHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(request);
            final Supplier<InputStream> content = response.getContent();
            body = IOUtils.toString(content.get(), StandardCharsets.UTF_8);
            client.close();
        } catch (Exception e) {
            throw new RuntimeException("unable to call device farm endpoints " + e.getMessage());
        }
        return body;
    }
}
