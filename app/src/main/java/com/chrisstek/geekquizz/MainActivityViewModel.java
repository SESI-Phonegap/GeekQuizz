package com.chrisstek.geekquizz;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.chrisstek.geekquizz.client.model.LoginResponse;
import com.chrisstek.geekquizz.client.model.UpdateResponse;
import com.chrisstek.geekquizz.interactor.LoginInteractor;
import com.chrisstek.geekquizz.ui.SingleMediatorLiveEvent;
import com.chrisstek.geekquizz.ui.purchase.PurchaseRepository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivityViewModel extends ViewModel {
    private final PurchaseRepository repository;
    private LoginInteractor interactor;
    private boolean isShowLoading;
    private LoginResponse loginResponse;
    private UpdateResponse updateResponse;
    private UpdateResponse updateGemsResponse;
    private UpdateResponse updateEsferasResponse;
    private CompositeDisposable compositeDisposable;
    private Bitmap imgAvatar;
    private String b64;
    private final SingleMediatorLiveEvent<String> allMessages = new SingleMediatorLiveEvent<>();

    public MainActivityViewModel(LoginInteractor interactor, PurchaseRepository repository){
        this.repository = repository;
        this.interactor = interactor;
        this.isShowLoading = false;
        this.loginResponse = null;
        this.compositeDisposable = new CompositeDisposable();
        this.imgAvatar = null;
        this.updateResponse = null;
        this.updateGemsResponse = null;
        this.updateEsferasResponse = null;
        b64 = null;
    }

    public void onLogin(String userName, String password){
        this.isShowLoading = true;
        Disposable disposable = interactor.login(userName, password)
                .doOnError( error ->
                        allMessages.setValue("Login Error: " + error.getMessage()))
                .subscribe(loginResponse -> {
                    this.isShowLoading = false;
                    if (null != loginResponse){
                        this.loginResponse = loginResponse;
                    }
                }, Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    public void onUpdateAvatar(String userName, String pass, int idUser, String b64){
        this.isShowLoading = true;
        Disposable disposable = interactor.updateAvatar(userName, pass, idUser, b64)
                .doOnError(error -> {
                    this.isShowLoading = false;
                    this.allMessages.setValue("Update Avatar Error: "+ error.getMessage());
                }).subscribe(updateResponse -> {
                    this.isShowLoading = false;
                    if (null != updateResponse){
                        this.updateResponse = updateResponse;
                        this.allMessages.setValue("Actualizacion de Avatar" + updateResponse.getEstatus() + ": "+ updateResponse.getError());

                    }
                }, Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    public void onUpdateGems(String userName, String password, int idUser, int gemas) {
        this.isShowLoading = true;
        Disposable disposable = interactor.updateGems(userName, password, idUser, gemas)
                .doOnError(error -> {
                    this.isShowLoading = false;
                    this.allMessages.setValue("UpdateGems Error: "+error.getMessage());
                }).subscribe(updateResponse -> {
                    this.isShowLoading = false;
                    if (null != updateResponse){
                       this.updateGemsResponse = updateResponse;
                    } else {
                        setErrorMessage("Error en Update Gems");
                    }
                }, Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    public void onUpdateEsferas(String userName, String pass, int idUser, int esferas ){
        this.isShowLoading = true;
        Disposable disposable = interactor.updateEsferas(userName, pass, idUser, esferas)
                .doOnError(error -> {
                    this.isShowLoading = false;
                    this.allMessages.setValue("Update Esferas Error: "+error.getMessage());
                }).subscribe(updateResponse -> {
                    this.isShowLoading = false;
                    if (null != updateResponse){
                        this.updateEsferasResponse = updateResponse;
                    } else {
                        this.allMessages.setValue("Ocurrio un error");
                    }
                },Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    public void setB64(String b64){
        this.b64 = b64;
    }

    public String getB64(){
        return this.b64;
    }

    public UpdateResponse getUpdateResponse(){
        return this.updateResponse;
    }

    public LiveData<LoginResponse> getLoginResponse(){
        MediatorLiveData<LoginResponse> loginResponseMediatorLiveData = new MediatorLiveData<>();
        loginResponseMediatorLiveData.setValue(loginResponse);
        return loginResponseMediatorLiveData;
    }

    public LiveData<Boolean> isShowLoading(){
        MediatorLiveData<Boolean> isVisible = new MediatorLiveData<>();
        isVisible.setValue(this.isShowLoading);
        return isVisible;
    }

    public void setImageAvatar(Bitmap bitmap){
        this.imgAvatar = bitmap;
    }
    public Bitmap getImgAvatar(){
        return this.imgAvatar;
    }

    public LiveData<Integer> getMessages() {
        return repository.getMessages();
    }

    public LiveData<String> getMessagesViewModel(){
        return allMessages;
    }

    public void setErrorMessage(String error){
        allMessages.setValue(error);
    }

    public LifecycleObserver getBillingLifecycleObserver() {
        return repository.getBillingLifecycleObserver();
    }

    public static class MainActivityViewModelFactory implements
            ViewModelProvider.Factory {
        private final PurchaseRepository repository;
        private final LoginInteractor interactor;

        public MainActivityViewModelFactory(LoginInteractor interactor, PurchaseRepository tdr) {
            this.repository = tdr;
            this.interactor = interactor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
                return (T) new MainActivityViewModel(interactor, repository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
