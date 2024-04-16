package com.example.android_coins.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android_coins.models.PriceResponseDataSimple;


public interface IViewModelSimple {
    LiveData<PriceResponseDataSimple> getData();

    MutableLiveData<String> getErrorMessage();

    void getSimpleCoin(String uuid);

    int getFavoriteIcon(String uuid);

    int getFavoriteIconOnClick(String uuid);

}
