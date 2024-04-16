package com.example.android_coins.storage;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.android_coins.models.PriceResponseData;

import java.util.List;

public class DataRepository {

    private final SampleDao sampleDao;
    private LiveData<List<PriceResponseData>> data;

    public DataRepository(Context applicationContext) {
        AppDatabase database = AppDatabase.getDatabase(applicationContext);
        this.sampleDao = database.sampleDaoDao();
        this.data = sampleDao.getAll();
    }

    public LiveData<List<PriceResponseData>> changeSource(String sortType) {
        if (sortType.equals("DESC")) {
            return this.getDataDesc();
        }
        if (sortType.equals("ASC")) {
            return this.getDataAsc();
        }
        if (sortType.equals("")) {
            return this.getData();
        }
        return this.getData();
    }

    public LiveData<List<PriceResponseData>> getDataAsc() {
        return this.sampleDao.getAllAsc();
    }

    public LiveData<List<PriceResponseData>> getDataDesc() {
        return this.sampleDao.getAllDesc();
    }

    public void setData() {
        this.data = this.sampleDao.getAll();
    }

    public LiveData<List<PriceResponseData>> getData() {
        return data;
    }

    public void insertData(PriceResponseData priceResponseData) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            sampleDao.insert(priceResponseData);
        });
    }

}
