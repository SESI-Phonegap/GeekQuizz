package com.chrisstek.geekquizz.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.chrisstek.geekquizz.MainActivity;
import com.chrisstek.geekquizz.R;
import com.chrisstek.geekquizz.client.QuizzClient;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.User;
import com.chrisstek.geekquizz.databinding.ActivityLoginBinding;
import com.chrisstek.geekquizz.interactor.LoginInteractor;
import com.facebook.FacebookSdk;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel.LoginActivityViewModelFactory loginActivityViewModelFactory = new LoginViewModel.LoginActivityViewModelFactory(new LoginInteractor(new QuizzClient()));
        loginViewModel = new ViewModelProvider(this, loginActivityViewModelFactory).get(LoginViewModel.class);
        loginViewModel.getMessages().observe(this, message -> {
            Snackbar snackbar = Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_SHORT);
            snackbar.show();
        });
        binding.setLifecycleOwner(this);
        binding.setLoginActivity(this);

    }

    public LiveData<Boolean> isShowLoading(){
        return loginViewModel.isShowLoading();
    }

    public void login(String userName, String password){
        loginViewModel.login(userName, password).observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (null != loginResponse){
                    openMenu(loginResponse.getUser());
                }
            };
        });
    }

    public void openMenu(User user){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}