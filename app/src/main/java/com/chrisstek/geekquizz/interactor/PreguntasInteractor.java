package com.chrisstek.geekquizz.interactor;

import com.chrisstek.geekquizz.client.QuizzServiceClient;
import com.chrisstek.geekquizz.client.model.Preguntas;
import com.chrisstek.geekquizz.client.model.UpdateResponse;

import java.util.List;

import io.reactivex.Observable;

public class PreguntasInteractor {
    private QuizzServiceClient quizServiceClient;

    public PreguntasInteractor(QuizzServiceClient quizServiceClient){
        this.quizServiceClient = quizServiceClient;
    }

    public Observable<List<Preguntas>> preguntasByAnimeAndLevel(String userName, String pass, int idAnime, int level){
        return quizServiceClient.getQuestionsByAnimeAndLevel(userName,pass,idAnime,level);
    }

    public Observable<UpdateResponse> updateLevelScoreGems(String userName, String pass, int gemas, int score, int level, int idUser,
                                                           int idAnime){
        return quizServiceClient.updateLevelScoreGems(userName,pass,gemas,score,level,idUser,idAnime);
    }

    public Observable<UpdateResponse> updateEsferas(String userName, String pass, int idUser, int esferas){
        return quizServiceClient.updateEsferas(userName,pass,idUser,esferas);
    }
}
