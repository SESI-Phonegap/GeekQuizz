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

public class LoginViewModel extends ViewModel {
    private LoginInteractor interactor;
    private LiveData<Boolean> isEnabled = new MutableLiveData<>();
    private final SingleMediatorLiveEvent<String> allMessages = new SingleMediatorLiveEvent<>();


    public LoginViewModel(LoginInteractor interactor){
        this.interactor = interactor;
        setEnabled(true);
    }

    public LiveData<LoginResponse> login(String userName, String password){
        return interactor.onLogin(userName, password);
    }

    public LiveData<LoginResponse> validaUsuarioFacebook(String idFacebook){
        return interactor.validaUsuarioFacebook(idFacebook);
    }

    public LiveData<Boolean> isEnabled(){
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled){
        MediatorLiveData<Boolean> isVisible = new MediatorLiveData<>();
        isVisible.setValue(isEnabled);
        this.isEnabled = isVisible;
    }

    public LiveData<String> getMessages(){
        return allMessages;
    }

    public void setErrorMessage(String error){
        allMessages.setValue(error);
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
