package com.example.android_coins.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "price_response_data")
public class PriceResponseData {
    @NonNull
    @PrimaryKey()
    private String uuid;
    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "olo")
    private String olo;
    @ColumnInfo(name = "symbol")
    private String symbol;

    public String getOlo() {
        return olo;
    }

    public void setOlo(String olo) {
        this.olo = olo;
    }

    @ColumnInfo(name = "name")
    private String name;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return this.symbol;
    }

}
