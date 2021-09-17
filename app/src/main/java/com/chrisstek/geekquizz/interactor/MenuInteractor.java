package com.chrisstek.geekquizz.interactor;

import com.chrisstek.geekquizz.client.QuizzServiceClient;
import com.chrisstek.geekquizz.client.model.Anime;
import com.chrisstek.geekquizz.client.model.ScoreResponse;

import java.util.List;

import io.reactivex.Observable;

public class MenuInteractor {
    private QuizzServiceClient quizServiceClient;

    public MenuInteractor(QuizzServiceClient quizServiceClient){
        this.quizServiceClient = quizServiceClient;
    }

    public Observable<List<Anime>> animes(String userName, String pass){
        return quizServiceClient.getAllAnimes(userName,pass);
    }

    public Observable<List<Anime>> getAnimesImg(String userName, String pass){
        return quizServiceClient.getAllAnimesImg(userName, pass);
    }

    public Observable<ScoreResponse> checkScoreAndLevel(String userName, String pass, int idAnime, int idUser){
        return quizServiceClient.checkScoreAndLevel(userName,pass,idAnime,idUser);
    }
}
