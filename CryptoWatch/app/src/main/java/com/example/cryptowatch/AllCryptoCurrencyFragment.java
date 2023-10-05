package com.example.cryptowatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCryptoCurrencyFragment extends Fragment {
    private RecyclerView serialRecycler;
    private ApiCoinGecko apiCoinGecko;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_crypto_currency, container, false);

        serialRecycler = view.findViewById(R.id.recycler_view);

        apiCoinGecko = RequestBuilder.buildRequest().create(ApiCoinGecko.class);
        Call<ArrayList<CryptoCurrencies>> getPetsList = apiCoinGecko.getCryptoCurrencyList();

        getPetsList.enqueue(new Callback<ArrayList<CryptoCurrencies>>() {
            @Override
            public void onResponse(Call<ArrayList<CryptoCurrencies>> call, Response<ArrayList<CryptoCurrencies>> response) {
                if(response.isSuccessful()){
                    serialRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    serialRecycler.setHasFixedSize(true);

                    ArrayList<CryptoCurrencies> listPets = response.body();
                    serialRecycler.setAdapter(new cryptocurrencyAdapter(getActivity() ,listPets));
                }else{
                    Toast.makeText(getContext(), "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CryptoCurrencies>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}