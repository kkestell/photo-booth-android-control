package org.kestell.photoboothcontrol;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class Api {
    public static String BASE_URL = "http://10.0.0.2:4567";

    private ApiClient client;

    public interface ApiClient {
        @POST("/photos")
        Call<Void> takePhotos();
    }

    public Api() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        client = retrofit.create(ApiClient.class);
    }

    public void takePhotos() {
        Call<Void> call = client.takePhotos();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}