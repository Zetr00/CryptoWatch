package com.example.cryptowatch;

import java.io.Serializable;

public class CryptoCurrencies implements Serializable {
    public String id;
    public String symbol;
    public String name;
    public String image;
    public Double current_price;
    public String price_change_percentage_24h;
    public int market_cap_rank;
    public Double price_change_percentage_1h_in_currency;
    public Double price_change_percentage_24h_in_currency;
    public Double price_change_percentage_7d_in_currency;
    public String market_cap;
    public String total_volume;
    public String circulating_supply;
    public String max_supply;
    public String high_24h;
    public String low_24h;
}
