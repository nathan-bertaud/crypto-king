package com.example.android_coins.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android_coins.models.PriceResponseData;

import java.util.List;

@Dao
public interface SampleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PriceResponseData priceResponseData);

    @Query("SELECT * FROM price_response_data ORDER BY name ASC")
    LiveData<List<PriceResponseData>> getAll();

    @Query("SELECT * FROM price_response_data ORDER BY price + 0 DESC")
    LiveData<List<PriceResponseData>> getAllDesc();

    @Query("SELECT * FROM price_response_data ORDER BY price + 0 ASC")
    LiveData<List<PriceResponseData>> getAllAsc();

}
