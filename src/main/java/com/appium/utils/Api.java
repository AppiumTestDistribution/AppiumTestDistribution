package com.appium.utils;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            body = response.body().string();
            response.close();

        } catch (Exception e) {
            throw new RuntimeException("unable to call device farm endpoints " + e.getMessage());
        }
        return body;
    }
}