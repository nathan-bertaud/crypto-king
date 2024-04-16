package com.example.android_coins.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_coins.R;
import com.example.android_coins.models.ApiDataSimple;
import com.example.android_coins.models.PriceResponseDataSimple;
import com.example.android_coins.network.RetrofitNetworkManager;
import com.example.android_coins.storage.PreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCoinDetails extends ViewModel implements IViewModelSimple {

    private final MutableLiveData<PriceResponseDataSimple> data = new MutableLiveData<>();

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    @Override
    public LiveData<PriceResponseDataSimple> getData() {
        return data;
    }


    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void getSimpleCoin(String uuid) {
        RetrofitNetworkManager.coinRankingAPI.getCoinDetails(uuid).enqueue(new Callback<ApiDataSimple>() {
            @Override
            public void onResponse(Call<ApiDataSimple> call, Response<ApiDataSimple> response) {
                if (response.body() != null) {
                    handleResponse(response.body());
                } else {
                    errorMessage.postValue("Le serveur renvoie des réponses vides");
                }
            }

            @Override
            public void onFailure(Call<ApiDataSimple> call, Throwable t) {
                t.printStackTrace();
                errorMessage.postValue("Le serveur renvoie une erreur");
            }
        });
    }

    @Override
    public int getFavoriteIcon(String uuid) {
        if (PreferencesHelper.getInstance().getFavorite(uuid).equals("1")) {
            return R.drawable.star;
        } else {
            return R.drawable.favorite;
        }
    }

    @Override
    public int getFavoriteIconOnClick(String uuid) {
        if (PreferencesHelper.getInstance().getFavorite(uuid).equals("1")) {
            PreferencesHelper.getInstance().setFavorite(uuid, "0");
            return R.drawable.favorite;
        } else {
            PreferencesHelper.getInstance().setFavorite(uuid, "1");
            return R.drawable.star;
        }
    }

    private void handleResponse(ApiDataSimple response) {
        data.postValue(response.getData().getCoin());
    }
}



