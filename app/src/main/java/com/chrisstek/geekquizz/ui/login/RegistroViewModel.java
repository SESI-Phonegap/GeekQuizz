package com.chrisstek.geekquizz.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.UpdateResponse;
import com.chrisstek.geekquizz.interactor.RegistroNuevoUsuarioInteractor;
import com.chrisstek.geekquizz.ui.SingleMediatorLiveEvent;

public class RegistroViewModel extends ViewModel {

    private RegistroNuevoUsuarioInteractor interactor;
    private final SingleMediatorLiveEvent<String> allMessages = new SingleMediatorLiveEvent<>();


    public RegistroViewModel(RegistroNuevoUsuarioInteractor registro) {
        this.interactor = registro;
    }

    public LiveData<UpdateResponse> registroNuevoUsuario(String userNameFriend, String username, String nombre, String email, int edad, String genero, String password) {
        return interactor.registroNuevoUsuario(userNameFriend, username, nombre, email, edad, genero, password);
    }

    public LiveData<LoginResponse> onLogin(String userName, String password){
        return interactor.login(userName, password);
    }

    public LiveData<String> getMessages(){
        return this.allMessages;
    }

    public void setErrorMessage(String error){
        allMessages.setValue(error);
    }

    public static class RegistroActivityViewModelFactory implements ViewModelProvider.Factory {

        private final RegistroNuevoUsuarioInteractor registro;

        public RegistroActivityViewModelFactory(RegistroNuevoUsuarioInteractor registro) {
            this.registro = registro;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if(modelClass.isAssignableFrom(RegistroViewModel.class)) {
                return (T) new RegistroViewModel(registro);
            }
            throw new IllegalArgumentException("Unknow ViewModel clasas");
        }
    }
}
