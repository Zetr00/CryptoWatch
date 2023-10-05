package com.example.cryptowatch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCoinGecko {
    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false&price_change_percentage=1h%2C24h%2C7d&locale=en")
    Call<ArrayList<CryptoCurrencies>> getCryptoCurrencyList();
}
