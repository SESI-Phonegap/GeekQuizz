package com.chrisstek.geekquizz.ui.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.chrisstek.geekquizz.client.model.Anime;
import com.chrisstek.geekquizz.client.model.ScoreResponse;
import com.chrisstek.geekquizz.interactor.MenuInteractor;
import com.chrisstek.geekquizz.ui.SingleMediatorLiveEvent;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AnimeCatalogViewModel {
    private MenuInteractor interactor;
    private boolean isShowLoading;
    private List<Anime> lstAnimes;
    private ScoreResponse scoreResponse;
    private CompositeDisposable compositeDisposable;
    private final SingleMediatorLiveEvent<String> allMessages = new SingleMediatorLiveEvent<>();

    public AnimeCatalogViewModel(MenuInteractor interactor){
        this.interactor = interactor;
        this.isShowLoading = false;
        this.lstAnimes = null;
        this.scoreResponse = null;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void getAllAnimes(String userName, String pass){
        this.isShowLoading = true;
        Disposable disposable = interactor.animes(userName,pass)
                .doOnError(error -> {
                    this.isShowLoading = false;
                    this.allMessages.setValue("Get Animes Error:" + error.getMessage());
                })
                .subscribe(animes -> {
                    this.isShowLoading = false;
                    if (!animes.isEmpty()){
                        this.lstAnimes = animes;
                    } else {
                        this.allMessages.setValue("Animes not found");
                    }
                }, Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    public void getAllAnimesImg(String userName, String pass){
        this.isShowLoading = true;
        Disposable disposable = interactor.getAnimesImg(userName, pass)
                .doOnError(error -> {
                    this.isShowLoading = false;
                    this.allMessages.setValue("Get Animes Error: "+error.getMessage());
                })
                .subscribe(animes -> {
                    this.isShowLoading = false;
                    if (!animes.isEmpty()) {
                        this.lstAnimes = animes;
                    } else {
                        this.allMessages.setValue("Animes not found");
                    }
                },Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    public void checkScoreAndLevel(String userName, String pass, int idAnime, int idUser){
        this.isShowLoading = true;
        Disposable disposable = interactor.checkScoreAndLevel(userName, pass, idAnime, idUser)
                .doOnError(error -> {
                    this.isShowLoading = false;
                    this.allMessages.setValue("CheckScore Error: "+error.getMessage());
                }).subscribe(score -> {
                    this.isShowLoading = false;
                    if (null != score){
                        this.scoreResponse = score;
                    } else {
                        this.allMessages.setValue("Error in checkScore");
                    }
                },Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    public LiveData<Boolean> isShowLoading(){
        MediatorLiveData<Boolean> isVisible = new MediatorLiveData<>();
        isVisible.setValue(isShowLoading);
        return isVisible;
    }
}
