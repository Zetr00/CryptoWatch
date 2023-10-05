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

public class Favourite extends Fragment {
    private RecyclerView serialRecycler1;
    private ApiCoinGecko apiCoinGecko;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        serialRecycler1 = view.findViewById(R.id.recycler_view_favourite);

        apiCoinGecko = RequestBuilder.buildRequest().create(ApiCoinGecko.class);
        Call<ArrayList<CryptoCurrencies>> getPetsList = apiCoinGecko.getCryptoCurrencyList();

        getPetsList.enqueue(new Callback<ArrayList<CryptoCurrencies>>() {
            @Override
            public void onResponse(Call<ArrayList<CryptoCurrencies>> call, Response<ArrayList<CryptoCurrencies>> response) {
                if(response.isSuccessful()){
                    serialRecycler1.setLayoutManager(new LinearLayoutManager(getContext()));
                    serialRecycler1.setHasFixedSize(true);
                    ArrayList<CryptoCurrencies> listPets = response.body();
                    serialRecycler1.setAdapter(new favouriteCryptoCurrencyAdapter(getActivity() ,listPets, serialRecycler1));
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