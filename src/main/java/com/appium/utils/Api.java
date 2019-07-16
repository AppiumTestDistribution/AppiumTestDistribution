package com.appium.utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Api extends Helpers {

    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    public Response getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .build();
        try {
            Request request = new Request.Builder().url(url).build();
            return client.newCall(request).execute();
        } catch (Exception e) {
            return null;
        }

    }

    public Response requestBuilderWithBearerToken(String url, String userToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(url)
            .get()
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .addHeader("Authorization", "Bearer" + " " + userToken)
            .build();
        return client.newCall(request).execute();

    }

    public String uploadMultiPartFile(File filePath, String hostMachine) throws Exception {
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("uploaded_file", filePath.getName(),
                RequestBody.create(MEDIA_TYPE_PNG, filePath))
            .build();
        Request request = new Request.Builder().url("http://" + hostMachine
            + ":" + getRemoteAppiumManagerPort(hostMachine) + "/artifacts/upload")
            .post(requestBody).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String post(String url, String json) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return null;
        }
    }

    public String postWithNoBody(String url, String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(null, new byte[]{});
        Request request = new Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .addHeader("Authorization", "Bearer" + " " + token)
            .post(body)
            .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return null;
        }
    }



}
