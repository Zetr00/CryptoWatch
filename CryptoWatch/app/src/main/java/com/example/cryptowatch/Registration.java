package com.example.cryptowatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends Fragment implements View.OnClickListener{

    private EditText editTextLogin;
    private EditText editTextPassword;
    private EditText editTextNickname;
    private EditText editTextEmail;
    private Button buttonRegister;
    private TextView textViewAlreadyHaveAccount;
    private TextView textViewError;

    ApiDataBase apiDataBase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        editTextLogin = view.findViewById(R.id.editTextLogin);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextNickname = view.findViewById(R.id.editTextNickname);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        textViewError = view.findViewById(R.id.textViewError);
        buttonRegister = view.findViewById(R.id.buttonRegister);
        textViewAlreadyHaveAccount = view.findViewById(R.id.textViewAlreadyHaveAccount);

        buttonRegister.setOnClickListener(this);
        textViewAlreadyHaveAccount.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRegister:
                User users = new User();
                users.loginUsers = editTextLogin.getText().toString().trim();
                users.passwordUsers = editTextPassword.getText().toString().trim();
                users.nicknameUsers = editTextNickname.getText().toString().trim();
                users.emailUsers = editTextEmail.getText().toString().trim();
                apiDataBase = RequestBuilder.buildRequest1().create(ApiDataBase.class);
                Call<User> getUserList = apiDataBase.regUser(users);
                getUserList.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User users = response.body();
                        if(users != null){
                            Toast.makeText(getActivity(), "Успешная регистрация!", Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            Authorization authorization = new Authorization();
                            fragmentTransaction.replace(R.id.fragment_container, authorization);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        else{
                            Toast.makeText(getActivity(), "Неверные данные", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
                break;
            case R.id.textViewAlreadyHaveAccount:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Authorization authorization = new Authorization();
                fragmentTransaction.replace(R.id.fragment_container, authorization);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }
}