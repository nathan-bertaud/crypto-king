package com.example.android_coins.network;

import com.example.android_coins.NetworkConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetworkManager {

    public static final CoinRankingAPI coinRankingAPI;

    static {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new ApiInterceptor())
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        coinRankingAPI = retrofit.create(CoinRankingAPI.class);
    }
}
