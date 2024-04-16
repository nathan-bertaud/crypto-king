package com.example.android_coins.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_coins.models.PriceResponseData;

import java.util.ArrayList;
import java.util.List;

public interface IViewModel {
    LiveData<List<PriceResponseData>> getData();

    MutableLiveData<String> getErrorMessage();

    void getCoinsList();

    void setSelectedCoin(String name);

    String PREFERENCES_KEY = "coinUuid";

    int checkFavorite(ArrayList<PriceResponseData> localDataSet, final int position);

    void sort(String sortType);

    void initializeDataSource();

    void refreshData(String sortingMode);
}
