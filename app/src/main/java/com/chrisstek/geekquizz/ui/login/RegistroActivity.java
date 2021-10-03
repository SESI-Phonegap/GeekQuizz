package com.chrisstek.geekquizz.ui.login;

import static com.chrisstek.geekquizz.utils.UtilInternetConnection.isOnline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chrisstek.geekquizz.MainActivity;
import com.chrisstek.geekquizz.R;
import com.chrisstek.geekquizz.client.QuizzClient;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.UpdateResponse;
import com.chrisstek.geekquizz.databinding.ActivityRegistroBinding;
import com.chrisstek.geekquizz.interactor.RegistroNuevoUsuarioInteractor;
import com.google.android.material.snackbar.Snackbar;

public class RegistroActivity extends AppCompatActivity {
    private RegistroViewModel registroViewModel;
    private ActivityRegistroBinding binding;
    private LifecycleOwner lifecycleOwner;
    private String genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registro);
        RegistroViewModel.RegistroActivityViewModelFactory registroActivityViewModelFactory = new RegistroViewModel.RegistroActivityViewModelFactory(new RegistroNuevoUsuarioInteractor(new QuizzClient()));
        registroViewModel = new ViewModelProvider(this, registroActivityViewModelFactory).get(RegistroViewModel.class);
        registroViewModel.getMessages().observe(this, message -> {
            Snackbar snackbar = Snackbar.make(binding.registroMainLayout, message, Snackbar.LENGTH_SHORT);
            snackbar.show();
        });
        binding.setLifecycleOwner(this);
        binding.setViewModel(registroViewModel);
        binding.setRegistroActivity(this);
        lifecycleOwner = this;
        genero = "";
        binding.radioGroup.setOnCheckedChangeListener(((radioGroup, id) -> genero = id == R.id.radioHombre ? "H" : "M"));
    }

    public void registroNuevoUsuario(String userNameFriend, String username, String nombre, String email, String edad, String password) {
        Log.i("registro", "Click");
        if (isOnline(getApplicationContext())) {
            if(username.isEmpty() || nombre.isEmpty() || email.isEmpty() || edad.isEmpty() || genero.isEmpty() || password.isEmpty()){
                registroViewModel.setErrorMessage(getString(R.string.datosincompletos));
            } else {
                binding.pbRegistro.setVisibility(View.VISIBLE);
                registroViewModel.registroNuevoUsuario(userNameFriend, username, nombre, email, Integer.parseInt(edad), genero, password).observe(lifecycleOwner, new Observer<UpdateResponse>() {
                    @Override
                    public void onChanged(UpdateResponse updateResponse) {
                        binding.pbRegistro.setVisibility(View.GONE);
                        if (null != updateResponse) {
                            if (updateResponse.getEstatus().equals(getString(R.string.statu_200))) {
                                login(username, password);
                            } else {
                                registroViewModel.setErrorMessage("Error: " + updateResponse.getError());
                            }
                        }
                    }
                });
            }
        } else {
            registroViewModel.setErrorMessage(getString(R.string.noInternet));
        }
    }

    public void login(String userName, String password){
        binding.pbRegistro.setVisibility(View.VISIBLE);
        registroViewModel.onLogin(userName, password).observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                binding.pbRegistro.setVisibility(View.GONE);

                if (null != loginResponse){
                    if (loginResponse.getEstatus().equals(getString(R.string.statu_200))){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user", loginResponse.getUser());
                        startActivity(intent);
                        finish();
                    } else {
                        registroViewModel.setErrorMessage("Usuario y/o contraseña incorrectos !");
                    }
                }
            }
        });
    }
}