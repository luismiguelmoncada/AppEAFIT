package com.universidadeafit.appeafit.ApiRest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.universidadeafit.appeafit.Model.Constants.BASE_URL;

/**
 * Created by LUISM on 30/12/2016.
 */

public class ApiClient {
    private static RestApi REST_CLIENT;

    static {
        setupRestClient();
    }

    private ApiClient() {}

    private static void setupRestClient() {

        OkHttpClient okClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                //.setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okClient)
                .build();

        REST_CLIENT = retrofit.create(RestApi.class);
    }

    public static RestApi get() {
        return REST_CLIENT;
    }
}
