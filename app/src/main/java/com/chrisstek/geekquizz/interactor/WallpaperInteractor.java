package com.chrisstek.geekquizz.interactor;

import com.chrisstek.geekquizz.client.QuizzServiceClient;
import com.chrisstek.geekquizz.client.model.Anime;
import com.chrisstek.geekquizz.client.model.UpdateResponse;
import com.chrisstek.geekquizz.client.model.Wallpaper;

import java.util.List;

import io.reactivex.Observable;

public class WallpaperInteractor {
    private QuizzServiceClient quizServiceClient;

    public WallpaperInteractor(QuizzServiceClient quizServiceClient){
        this.quizServiceClient = quizServiceClient;
    }

    public Observable<List<Anime>> getAnimesForWallpaper(String userName, String pass){
        return quizServiceClient.getAllAnimesForWallpaper(userName,pass);
    }

    public Observable<List<Wallpaper>> getWallpaperByAnime(String userName, String pass, int idAnime){
        return quizServiceClient.getWallpaperByAnime(userName,pass,idAnime);
    }

    public Observable<UpdateResponse> updateGemas(String userName, String pass, int idUser, int gemas){
        return quizServiceClient.updateGemas(userName,pass,idUser,gemas);
    }

    public Observable<List<Wallpaper>> getAvatarsByAnime(String userName, String pass, int idAnime){
        return quizServiceClient.getAvatarsByAnime(userName,pass,idAnime);
    }
}
