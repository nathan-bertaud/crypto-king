package com.example.android_coins.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android_coins.CoinApplication;


public class PreferencesHelper {
    private static final String SHARED_PREFERENCES_NAME = "coinsPreferences";
    private static final String COIN_SELECTED = "coinSelected";
    private static final String SORTING_MODE = "sortingMode";
    private static PreferencesHelper INSTANCE;
    private final SharedPreferences preferences;

    private PreferencesHelper() {
        preferences = CoinApplication.getContext()
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesHelper();
        }
        return INSTANCE;
    }

    public String getSelected() {
        return preferences.getString(COIN_SELECTED, "noCoinSelected");
    }

    public void setSelected(String value) {
        preferences.edit().putString(COIN_SELECTED, value).apply();
    }


    public String getFavorite(String key) {
        return preferences.getString(key, "notFavorite");
    }

    public void setFavorite(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }
}
