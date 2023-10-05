package com.example.cryptowatch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authorization extends Fragment implements View.OnClickListener{
    private EditText editTextLogin;
    private EditText editTextPassword;
    private TextView textViewError;
    private Button buttonLogin;
    private TextView textViewNoAccount;

    ApiDataBase apiDataBase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authorization, container, false);

        // Находим все нужные элементы в макете
        editTextLogin = view.findViewById(R.id.editTextLogin);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        textViewError = view.findViewById(R.id.textViewError);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        textViewNoAccount = view.findViewById(R.id.textViewNoAccount);

        // Назначаем обработчики нажатия на элементы
        buttonLogin.setOnClickListener(this);
        textViewNoAccount.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                User users = new User();
                users.loginUsers = editTextLogin.getText().toString().trim();
                users.passwordUsers = editTextPassword.getText().toString().trim();
                users.nicknameUsers = "string";
                users.emailUsers = "weufhwieuhf@mail.ru";
                apiDataBase = RequestBuilder.buildRequest1().create(ApiDataBase.class);
                Call<User> getUserList = apiDataBase.authUser(users);
                getUserList.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User users = response.body();
                        if(users != null){
                            Toast.makeText(getActivity(), "Успешная авторизация!", Toast.LENGTH_SHORT).show();
                            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomnavigation);
                            Menu menu = bottomNavigationView.getMenu();
                            MenuItem menuItem = menu.findItem(R.id.navigation_portfolio);
                            menuItem.setVisible(true);

                            MenuItem menuItem1 = menu.findItem(R.id.navigation_authorization);
                            menuItem1.setVisible(false);

                            MenuItem menuItem2 = menu.findItem(R.id.navigation_exit);
                            menuItem2.setVisible(true);

                            String Login = users.loginUsers;
                            int IDUSER = users.idUsers;

                            SharedPreferences sharedPref = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.remove("login");
                            editor.remove("iduser");
                            editor.putString("login", Login);
                            editor.putInt("iduser", IDUSER);
                            editor.apply();

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            AllCryptoCurrencyFragment allCryptoCurrencyFragment = new AllCryptoCurrencyFragment();
                            fragmentTransaction.replace(R.id.fragment_container, allCryptoCurrencyFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        else{
                            Toast.makeText(getActivity(), "Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getActivity(), "Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.textViewNoAccount:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Registration registration = new Registration();
                fragmentTransaction.replace(R.id.fragment_container, registration);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }
}
