package com.chrisstek.geekquizz.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.interactor.LoginInteractor;
import com.chrisstek.geekquizz.ui.SingleMediatorLiveEvent;

import io.reactivex.disposables.CompositeDisposable;

public class LoginViewModel extends ViewModel {
    private LoginInteractor interactor;
    private boolean isShowLoading;
    private LiveData<LoginResponse> loginResponse;
    private MutableLiveData<LoginResponse> mutableLoginResponse;
    private CompositeDisposable compositeDisposable;
    private final SingleMediatorLiveEvent<String> allMessages = new SingleMediatorLiveEvent<>();


    public LoginViewModel(LoginInteractor interactor){
        this.interactor = interactor;
        this.isShowLoading = false;
        this.loginResponse = new MutableLiveData<>();
        this.mutableLoginResponse = null;
        this.compositeDisposable = new CompositeDisposable();
    }

    public LiveData<LoginResponse> login(String userName, String password){
        return interactor.onLogin(userName, password);
    }

    public LiveData<Boolean> isShowLoading(){
        MediatorLiveData<Boolean> isVisible = new MediatorLiveData<>();
        isVisible.setValue(this.isShowLoading);
        return isVisible;
    }

    public LiveData<String> getMessages(){
        return allMessages;
    }

    public LiveData<LoginResponse> getLoginResponse(){
        return this.loginResponse;
    }

    public static class LoginActivityViewModelFactory implements ViewModelProvider.Factory {

        private  final LoginInteractor loginInteractor;
        public LoginActivityViewModelFactory(LoginInteractor loginInteractor){
            this.loginInteractor = loginInteractor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if(modelClass.isAssignableFrom(LoginViewModel.class)){
                return (T) new LoginViewModel(loginInteractor);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
