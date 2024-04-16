package com.example.android_coins.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.android_coins.models.ApiData;
import com.example.android_coins.models.PriceResponseData;
import com.example.android_coins.network.RetrofitNetworkManager;
import com.example.android_coins.storage.DataRepository;
import com.example.android_coins.storage.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitViewModel extends AndroidViewModel implements IViewModel {

    private final DataRepository dataRepository;
    private LiveData<List<PriceResponseData>> data;

    private final MutableLiveData<String> sortInput = new MutableLiveData<>();

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void initializeDataSource() {
        sortInput.setValue("");
        data = Transformations.switchMap(sortInput,
                this.dataRepository::changeSource);
    }

    @Override
    public void sort(String sortType) {
        sortInput.setValue(sortType);
    }

    public RetrofitViewModel(Application application) {
        super(application);
        this.dataRepository = new DataRepository(application);
        this.data = dataRepository.getData();
    }

    public LiveData<List<PriceResponseData>> getData() {
        return data;
    }

    @Override
    public void getCoinsList() {
        RetrofitNetworkManager.coinRankingAPI.getCoinsPrice().enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(@NonNull Call<ApiData> call, @NonNull Response<ApiData> response) {
                if (response.body() != null) {
                    handleResponse(response.body());
                } else {
                    errorMessage.postValue("Le serveur renvoie des réponses vides");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiData> call, @NonNull Throwable t) {
                t.printStackTrace();
                errorMessage.postValue("Le serveur renvoie une erreur");
            }
        });
    }

    private void handleResponse(ApiData response) {
        for (PriceResponseData coin : response.getData().getCoins()) {
            dataRepository.insertData(coin);
        }
    }

    @Override
    public void setSelectedCoin(String name) {
        PreferencesHelper.getInstance().setSelected(name);
    }

    @Override
    public void refreshData(String sortingMode) {
        this.getCoinsList();
        this.sort(sortingMode);
    }

    @Override
    public int checkFavorite(ArrayList<PriceResponseData> localDataSet, final int position) {
        if (!PreferencesHelper.getInstance().getFavorite(localDataSet.get(position).getUuid()).equals("1")) {
            return View.GONE;
        } else {
            return View.VISIBLE;
        }
    }
}