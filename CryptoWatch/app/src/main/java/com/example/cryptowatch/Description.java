package com.example.cryptowatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Description extends AppCompatActivity {

    private RecyclerView serialRecycler;
    private ApiDataBase apiDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        WebView webView = (WebView) findViewById(R.id.tradingview);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setInitialScale(120);
        Intent intent = getIntent();
        CryptoCurrencies cryptoCurrencies = (CryptoCurrencies) intent.getSerializableExtra("sc");
        webView.loadUrl("https://s.tradingview.com/widgetembed/?frameElementId=tradingview_7caef&symbol=" + cryptoCurrencies.symbol.toUpperCase() + "USDT&interval=D&hidesidetoolbar=0&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=%5B%5D&hideideas=1&theme=Light&style=1&timezone=Etc%2FUTC&studies_overrides=%7B%7D&overrides=%7B%7D&enabled_features=%5B%5D&disabled_features=%5B%5D&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT#%7B%22page-uri%22%3A%22coinmarketcap.com%2Fcurrencies%2Fbitcoin%2F%22%7D");

            TextView tvPriceChangePercentage1hInCurrency = findViewById(R.id.tv_price_change_percentage_1h_in_currency);
            tvPriceChangePercentage1hInCurrency.setText(String.format("%.5f", cryptoCurrencies.price_change_percentage_1h_in_currency) + "%");

            TextView tvPriceChangePercentage24hInCurrency = findViewById(R.id.tv_price_change_percentage_24h_in_currency);
            tvPriceChangePercentage24hInCurrency.setText(String.format("%.5f", cryptoCurrencies.price_change_percentage_24h_in_currency) + "%");

            TextView tvPriceChangePercentage7dInCurrency = findViewById(R.id.tv_price_change_percentage_7d_in_currency);
            tvPriceChangePercentage7dInCurrency.setText(String.format("%.5f", cryptoCurrencies.price_change_percentage_7d_in_currency) + "%");

            TextView tvMarketCap = findViewById(R.id.tv_market_cap);
            tvMarketCap.setText("$" + shortenNumber(cryptoCurrencies.market_cap));

            TextView tvTotalVolume = findViewById(R.id.tv_total_volume);
            tvTotalVolume.setText("$" + shortenNumber(cryptoCurrencies.total_volume));

            TextView tvCirculatingSupply = findViewById(R.id.tv_circulating_supply);
            tvCirculatingSupply.setText(shortenNumber(cryptoCurrencies.circulating_supply));

            TextView tvMaxSupply = findViewById(R.id.tv_max_supply);
            tvMaxSupply.setText(shortenNumber(cryptoCurrencies.max_supply));

            TextView tvHigh24h = findViewById(R.id.tv_high_24h);
            tvHigh24h.setText("$" + cryptoCurrencies.high_24h);

            TextView tvLow24h = findViewById(R.id.tv_low_24h);
            tvLow24h.setText("$" + cryptoCurrencies.low_24h);

        serialRecycler = findViewById(R.id.recuclerviewcomment);

        apiDataBase = RequestBuilder.buildRequest1().create(ApiDataBase.class);
        Call<ArrayList<Message>> getMessage = apiDataBase.getComments();

        getMessage.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                if(response.isSuccessful()){
                    serialRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    serialRecycler.setHasFixedSize(true);

                    ArrayList<Message> listMessage = response.body();
                    serialRecycler.setAdapter(new messageAdapter(listMessage));
                }else{
                    Toast.makeText(getApplicationContext(), "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String login = sharedPref.getString("login", "");
        if(login.equals("not")){
            CardView cardView = findViewById(R.id.cardView3);
            EditText editText = findViewById(R.id.profile_message_edit);
            Button button = findViewById(R.id.buttonMessage);

            cardView.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
        else{
            CardView cardView = findViewById(R.id.cardView3);
            EditText editText = findViewById(R.id.profile_message_edit);
            Button button = findViewById(R.id.buttonMessage);

            cardView.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }

        Button buttonMessage = findViewById(R.id.buttonMessage);
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
                String login = sharedPref.getString("login", "");
                int IDUser = sharedPref.getInt("iduser", 1);
                Message message = new Message();
                EditText profile_message_edit = (EditText) findViewById(R.id.profile_message_edit);
                message.com = profile_message_edit.getText().toString().trim();
                message.usersId = IDUser;
                apiDataBase = RequestBuilder.buildRequest1().create(ApiDataBase.class);
                profile_message_edit.setText("");
                Call<Message> getUserList = apiDataBase.postComments(message);
                getUserList.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Message message1 = response.body();
                        if (message1 != null) {
                            apiDataBase = RequestBuilder.buildRequest1().create(ApiDataBase.class);
                            Call<ArrayList<Message>> getMessage = apiDataBase.getComments();

                            getMessage.enqueue(new Callback<ArrayList<Message>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                                    if(response.isSuccessful()){
                                        serialRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        serialRecycler.setHasFixedSize(true);

                                        ArrayList<Message> listMessage = response.body();
                                        serialRecycler.setAdapter(new messageAdapter(listMessage));
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public static String shortenNumber(String numberString) {
        if(numberString != null) {
            double number = Double.parseDouble(numberString);
            String suffix = "";

            if (number >= 1000000000) {
                number /= 1000000000;
                suffix = "B";
            } else if (number >= 1000000) {
                number /= 1000000;
                suffix = "M";
            } else if (number >= 1000) {
                number /= 1000;
                suffix = "K";
            }

            return String.format("%.1f%s", number, suffix);
        } else {
            String temp = "-";
            return temp;
        }
    }

}
