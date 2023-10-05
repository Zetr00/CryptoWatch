package com.example.cryptowatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cryptocurrencyAdapter extends RecyclerView.Adapter<cryptocurrencyAdapter.CryptoViewHolder> {
    private ArrayList<CryptoCurrencies> cryptoCurrencies;
    private Context context;

    public cryptocurrencyAdapter(Context context, ArrayList<CryptoCurrencies> cryptoCurrencies){
        this.cryptoCurrencies = cryptoCurrencies;
        this.context = context;
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new CryptoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        holder.bind(cryptoCurrencies.get(position));
    }

    @Override
    public int getItemCount() {
        return cryptoCurrencies.size();
    }

    class CryptoViewHolder extends RecyclerView.ViewHolder{

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
            });
        }


        public void bind(CryptoCurrencies cryptoCurrencies){
            final ApiDataBase[] apiDataBase = new ApiDataBase[1];
            final ApiDataBase[] apiDataBase1 = new ApiDataBase[1];
            final ApiDataBase[] apiDataBase2 = new ApiDataBase[1];
            final ApiDataBase[] apiDataBase3 = new ApiDataBase[1];
            SharedPreferences sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
            String login = sharedPref.getString("login", "");
            int IDUser = sharedPref.getInt("iduser", 1);
            final int[] currentIcon = {1};
            int[] icons = {R.drawable.star_241, R.drawable.star_24};

            ImageView Iv = itemView.findViewById(R.id.image);
            TextView Tv2 = itemView.findViewById(R.id.name);
            TextView Tv3 = itemView.findViewById(R.id.symbol);
            TextView Tv4 = itemView.findViewById(R.id.current_price);
            TextView Tv5 = itemView.findViewById(R.id.priceChange);
            TextView Tv6 = itemView.findViewById(R.id.number);
            ImageView Iv7 = itemView.findViewById(R.id.icon_clickable);

            Drawable drawable = context.getResources().getDrawable(R.drawable.star_24);
            Drawable drawable1 = context.getResources().getDrawable(R.drawable.star_241);

            apiDataBase1[0] = RequestBuilder.buildRequest1().create(ApiDataBase.class);
            Call<ArrayList<TrackingList1>> getMessage = apiDataBase1[0].getTrackingList();

            getMessage.enqueue(new Callback<ArrayList<TrackingList1>>() {
                @Override
                public void onResponse(Call<ArrayList<TrackingList1>> call, Response<ArrayList<TrackingList1>> response) {
                    if(response.isSuccessful()){
                        if(login.equals("not")){}
                        else{
                        ArrayList<TrackingList1> listMessage = response.body();
                        for (TrackingList1 trackingList1 : listMessage) {
                            if (trackingList1.nameCrypto.equals(cryptoCurrencies.id)) {
                                if(trackingList1.usersId == IDUser){
                                    Iv7.setImageDrawable(drawable1);
                                }
                            }
                        }
                        }
                    }else{
                        Toast.makeText(context, "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<TrackingList1>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            if(login.equals("not")){
                Iv7.setVisibility(View.GONE);
            }
            else{
                 Iv7.setVisibility(View.VISIBLE);
            }
            Picasso.get().load(cryptoCurrencies.image).into(Iv);
            Tv2.setText(cryptoCurrencies.name);
            Tv3.setText(cryptoCurrencies.symbol.toUpperCase());
            Tv4.setText(cryptoCurrencies.current_price + "$");
            char firstChar = cryptoCurrencies.price_change_percentage_24h.charAt(0);
            if (firstChar == '-') {
                Tv5.setTextColor(Color.RED);
            } else if (firstChar == '+') {
            } else {
                Tv5.setTextColor(Color.GREEN);
            }
            Tv5.setText(cryptoCurrencies.price_change_percentage_24h + "%");
            Tv6.setText("№" + cryptoCurrencies.market_cap_rank);

            Iv7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIcon[0] = (currentIcon[0] + 1) % icons.length;
                    Iv7.setImageResource(icons[currentIcon[0]]);
                    Drawable drawable = Iv7.getDrawable();
                    if (currentIcon[0] == 0) {
                        TrackingList trackingList = new TrackingList();
                        trackingList.nameCrypto = cryptoCurrencies.id;
                        trackingList.usersId = IDUser;
                        apiDataBase[0] = RequestBuilder.buildRequest1().create(ApiDataBase.class);
                        Call<TrackingList> getUserList = apiDataBase[0].postTrackingList(trackingList);
                        getUserList.enqueue(new Callback<TrackingList>() {
                            @Override
                            public void onResponse(Call<TrackingList> call, Response<TrackingList> response) {
                                TrackingList trackingList1 = response.body();
                                if (trackingList1 != null) {


                                } else {
                                    Toast.makeText(context, "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TrackingList> call, Throwable t) {
                                Toast.makeText(context, "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        apiDataBase2[0] = RequestBuilder.buildRequest1().create(ApiDataBase.class);
                        Call<ArrayList<TrackingList2>> getMessage1 = apiDataBase2[0].getTrackingList2();

                        getMessage1.enqueue(new Callback<ArrayList<TrackingList2>>() {
                            @Override
                            public void onResponse(Call<ArrayList<TrackingList2>> call, Response<ArrayList<TrackingList2>> response) {
                                if(response.isSuccessful()){
                                    if(login.equals("not")){}
                                    else{
                                        ArrayList<TrackingList2> listMessage1 = response.body();
                                        for (TrackingList2 trackingList2 : listMessage1) {
                                            if (trackingList2.nameCrypto.equals(cryptoCurrencies.id)) {
                                                if(trackingList2.usersId == IDUser){
                                                    apiDataBase3[0] = RequestBuilder.buildRequest1().create(ApiDataBase.class);
                                                    Call<TrackingList1> getMessage3 = apiDataBase3[0].deleteTrackingList(trackingList2.idTrackingList);

                                                    getMessage3.enqueue(new Callback<TrackingList1>() {
                                                        @Override
                                                        public void onResponse(Call<TrackingList1> call, Response<TrackingList1> response) {
                                                            if(response.isSuccessful()){
                                                            }else{
                                                                Toast.makeText(context, "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<TrackingList1> call, Throwable t) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    Toast.makeText(context, "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<TrackingList2>> call, Throwable t) {

                            }
                        });
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, Description.class);
                    intent.putExtra("sc", cryptoCurrencies);
                    context.startActivity(intent);
                }
            });
        }
    }

}

