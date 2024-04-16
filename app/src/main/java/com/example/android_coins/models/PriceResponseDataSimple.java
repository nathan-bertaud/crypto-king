package com.example.android_coins.models;

public class PriceResponseDataSimple {

    private String price;

    private String symbol;

    private String iconUrl;

    private String name;

    private String uuid;

    private AllTimeHigh allTimeHigh;

    public AllTimeHigh getAllTimeHigh() {
        return allTimeHigh;
    }

    public void setAllTimeHigh(AllTimeHigh allTimeHigh) {
        this.allTimeHigh = allTimeHigh;
    }

    public String getIconUrl() {
        return iconUrl;
    }


    public String getUuid() {
        return uuid;
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
