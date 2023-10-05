package com.example.cryptowatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Exit extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("login");
        editor.putString("login", "not");
        editor.apply();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomnavigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.navigation_portfolio);
        menuItem.setVisible(false);

        MenuItem menuItem1 = menu.findItem(R.id.navigation_authorization);
        menuItem1.setVisible(true);

        MenuItem menuItem2 = menu.findItem(R.id.navigation_exit);
        menuItem2.setVisible(false);

        return inflater.inflate(R.layout.fragment_exit, container, false);
    }
}