package com.chrisstek.geekquizz.ui.login;

import static com.chrisstek.geekquizz.utils.UtilInternetConnection.isOnline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.chrisstek.geekquizz.MainActivity;
import com.chrisstek.geekquizz.R;
import com.chrisstek.geekquizz.client.QuizzClient;
import com.chrisstek.geekquizz.client.model.FacebookLoginModel;
import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.User;
import com.chrisstek.geekquizz.databinding.ActivityLoginBinding;
import com.chrisstek.geekquizz.interactor.LoginInteractor;
import com.chrisstek.geekquizz.utils.UtilInternetConnection;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private CallbackManager callbackManager;
    private static final String FACEBOOK_EMAIL = "email";
    private static final String FACEBOOK_NAME = "name";
    private FacebookLoginModel facebookLoginModel;
    private LifecycleOwner lifecycleOwner;

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

        binding.setViewModel(loginViewModel);
        binding.setLoginActivity(this);
        lifecycleOwner = this;

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loginViewModel.setEnabled(true);
    }

    public LiveData<Boolean> isShowLoading(){
        return loginViewModel.isShowLoading();
    }

    public void login(String userName, String password){
        if (isOnline(getApplicationContext())) {
            loginViewModel.setIsShowLoadin(true);
            loginViewModel.login(userName, password).observe(lifecycleOwner, new Observer<LoginResponse>() {
                @Override
                public void onChanged(LoginResponse loginResponse) {
                    loginViewModel.setIsShowLoadin(false);
                    if (null != loginResponse) {
                        if (loginResponse.getEstatus().equals("200")) {
                            openMenu(loginResponse.getUser());
                        } else {
                            loginViewModel.setErrorMessage("Usuario y/o contraseña incorrectos !");
                        }
                    }
                }
            });
        } else {
            loginViewModel.setErrorMessage(getString(R.string.noInternet));
        }
    }

    private void facebookLogin(){
        //Facebook
        callbackManager = CallbackManager.Factory.create();
        facebookLoginModel = new FacebookLoginModel();
        LoginButton btnLoginFacebook = findViewById(R.id.btn_login_facebook);
        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile",FACEBOOK_EMAIL));
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginViewModel.setIsShowLoadin(true);
                loginViewModel.setEnabled(false);
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), ((object, response) -> {
                    loginViewModel.setIsShowLoadin(false);
                    loginViewModel.setEnabled(true);
                   try {
                       //  URL photoFace = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
                       facebookLoginModel.setFacebookId(object.getString("id"));
                       facebookLoginModel.setFacebookUserName(object.getString(FACEBOOK_NAME));
                       facebookLoginModel.setFacebookUserEmail(object.getString(FACEBOOK_EMAIL));

                       if (isOnline(getApplicationContext())) {
                           loginViewModel.setIsShowLoadin(true);
                           loginViewModel.setEnabled(false);
                           loginViewModel.validaUsuarioFacebook(facebookLoginModel.getFacebookId()).observe(lifecycleOwner, new Observer<LoginResponse>() {
                               @Override
                               public void onChanged(LoginResponse loginResponse) {
                                   loginViewModel.setIsShowLoadin(false);
                                   if (null != loginResponse) {
                                       openMenu(loginResponse.getUser());
                                   }
                               }
                           });
                       } else {
                           loginViewModel.setErrorMessage(getString(R.string.noInternet));
                       }
                   } catch (Exception e){
                       Log.e("Error-",e.getMessage());
                   }
                }));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public void openMenu(User user){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}