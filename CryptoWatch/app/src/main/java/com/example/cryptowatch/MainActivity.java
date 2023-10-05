package com.example.cryptowatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AllCryptoCurrencyFragment allCryptoCurrencyFragment = new AllCryptoCurrencyFragment();
        fragmentTransaction.replace(R.id.fragment_container, allCryptoCurrencyFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_all_currencies:
                                selectedFragment = new AllCryptoCurrencyFragment();
                                break;
                            case R.id.navigation_authorization:
                                selectedFragment = new Authorization();
                                break;
                            case R.id.navigation_portfolio:
                                selectedFragment = new Favourite();
                                break;
                            case R.id.navigation_exit:
                                selectedFragment = new Exit();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String login = sharedPref.getString("login", "");
        if(login.equals("not")){
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.navigation_portfolio);
            menuItem.setVisible(false);

            MenuItem menuItem1 = menu.findItem(R.id.navigation_authorization);
            menuItem1.setVisible(true);

            MenuItem menuItem2 = menu.findItem(R.id.navigation_exit);
            menuItem2.setVisible(false);
        }
        else{
            Menu menu = bottomNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.navigation_portfolio);
            menuItem.setVisible(true);

            MenuItem menuItem1 = menu.findItem(R.id.navigation_authorization);
            menuItem1.setVisible(false);

            MenuItem menuItem2 = menu.findItem(R.id.navigation_exit);
            menuItem2.setVisible(true);
        }
    }
}