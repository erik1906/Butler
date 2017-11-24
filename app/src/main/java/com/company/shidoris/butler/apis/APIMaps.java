package com.company.shidoris.butler.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Erik on 23/11/2017.
 */

public class APIMaps {
    public static final String CLIENT_ID = "AIzaSyBnPBgz6xWMp93mYhZea9od7jX0HByFF-c";

    public static final int REQUEST_CODE_FSQ_CONNECT = 200; //Connection code
    public static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;

    public static final String BASE_URL = "https://roads.googleapis.com/v1/";
    public static final String BASE_URL_DIRECTION = "https://maps.googleapis.com/maps/api/directions/";

    private static Retrofit retrofit = null;

    public static Retrofit getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getDirectionApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_DIRECTION)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
