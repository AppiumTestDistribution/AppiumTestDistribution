package com.appium.utils;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Api {

    public Response getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request).execute();
    }

    public String uploadMultiPartFile(File filePath, String hostMachine) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType MEDIA_TYPE_PNG = MediaType.parse("multipart/form-data");
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("uploaded_file", filePath.getName(),
                        RequestBody.create(MEDIA_TYPE_PNG, filePath))
                .build();
        Request request = new Request.Builder().url("http://" + hostMachine
                + ":4567/artifacts/upload")
                .post(requestBody).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
