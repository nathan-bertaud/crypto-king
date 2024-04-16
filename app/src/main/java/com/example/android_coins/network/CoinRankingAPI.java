package com.example.android_coins.network;

import com.example.android_coins.models.ApiData;
import com.example.android_coins.models.ApiDataSimple;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// https://square.github.io/retrofit/
public interface CoinRankingAPI {

    @GET("/coins")
    Call<ApiData> getCoinsPrice();

    @GET("/coin/{uuid}")
    Call<ApiDataSimple> getCoinDetails(@Path("uuid") String uuid);
}
