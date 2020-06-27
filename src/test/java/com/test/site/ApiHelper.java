package com.test.site;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiHelper {
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
        .connectTimeout(90, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .readTimeout(90, TimeUnit.SECONDS)
        .build();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Function<Request.Builder, ResponseBody> execute = req -> {
        try {
            Response response = CLIENT.newCall(req.build())
                .execute();
            return requireNonNull(response.body());
        } catch (IOException e) {
            return null;
        }
    };
    private final Function<String, Request.Builder> request = path ->
        new Request.Builder().url(path);

    public ResponseBody delete(String url) {
        return execute.apply(request.apply(url)
            .delete());
    }

    public ResponseBody get(String url) {
        return execute.apply(request.apply(url)
            .get());
    }

    public ResponseBody post(String url) {
        return execute.apply(request.apply(url)
            .post(new FormBody.Builder().build()));
    }
}